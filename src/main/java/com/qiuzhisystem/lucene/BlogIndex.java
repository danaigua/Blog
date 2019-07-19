package com.qiuzhisystem.lucene;

import java.io.StringReader;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.qiuzhisystem.entity.Blog;
import com.qiuzhisystem.utils.DateUtil;
import com.qiuzhisystem.utils.StringUtil;
import com.sun.tools.classfile.Opcode.Set;

/**
 * 博客索引类
 * @author JOB
 *
 */
public class BlogIndex {

	private Directory dir;
	/**
	 * 获取indexwriter实例
	 * @return
	 * @throws Exception
	 */
	private IndexWriter getWriter() throws Exception{
		dir = FSDirectory.open(Paths.get("C://lucene3"));
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		IndexWriter writer = new IndexWriter(dir, iwc);
		return writer;
	}
	/**
	 * 添加博客索引
	 * @param blog
	 * @throws Exception
	 */
	public void addIndex(Blog blog)throws Exception{
		IndexWriter writer = getWriter();
		Document doc = new Document();
		doc.add(new StringField("id", String.valueOf(blog.getId()), Field.Store.YES));
		doc.add(new TextField("title", blog.getTitle(), Field.Store.YES));
		doc.add(new StringField("releaseDate", DateUtil.formatDate(new Date(), "yyyy-MM-dd"), Field.Store.YES));
		doc.add(new TextField("content", blog.getContentNoTag(), Field.Store.YES));
		writer.addDocument(doc);
		writer.close();
	}
	/**
	 * 查询算法
	 * @param q
	 * @return
	 * @throws Exception
	 */
	public List<Blog> searchBlog(String q) throws Exception{
		dir = FSDirectory.open(Paths.get("C://lucene3"));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher is = new IndexSearcher(reader);
		//多条件查询
		BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		
		QueryParser parser = new QueryParser("title", analyzer);
		Query query = parser.parse(q);
		
		QueryParser parser2 = new QueryParser("content", analyzer);
		Query query2 = parser.parse(q);
		
		booleanQuery.add(query, BooleanClause.Occur.SHOULD);
		booleanQuery.add(query2, BooleanClause.Occur.SHOULD);
		
		TopDocs hits = is.search(booleanQuery.build(), 100);
		QueryScorer scorer = new QueryScorer(query);//得分
		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
		Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);//代码高亮
		Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
		highlighter.setTextFragmenter(fragmenter);
		List<Blog> blogList = new LinkedList<Blog>();
		for(ScoreDoc scoreDoc : hits.scoreDocs) {
			Document doc = is.doc(scoreDoc.doc);
			Blog blog = new Blog();
			blog.setId(Integer.parseInt(doc.get("id")));
			blog.setReleaseDateStr(doc.get("releaseDate"));
			String title = doc.get("title");
			//过滤html标签
			String content = StringEscapeUtils.escapeHtml(doc.get("content"));
			if(title != null) {
				TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
				String hTitle = highlighter.getBestFragment(tokenStream, title);
				if(StringUtil.isEmpty(hTitle)) {
					blog.setTitle(title);
				}else {
					blog.setTitle(hTitle);
				}
			}
			if(content != null) {
				TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(content));
				String hContent = highlighter.getBestFragment(tokenStream, content);
				if(StringUtil.isEmpty(content)) {
					if(content.length() < 200) {
						blog.setContent(content);
					}else {
						blog.setContent(content.substring(0, 200));
					}
				}else {
					blog.setContent(hContent);
				}
			}
			blogList.add(blog);
		}
		return blogList;
	}
	/**
	 * 升级搜索算法
	 * @param q
	 * @return
	 * @throws Exception
	 */
	public List<Blog> upgradeSearchBlog(String q)throws Exception{
		List<Blog> blogList = new LinkedList<Blog>();
		dir = FSDirectory.open(Paths.get("C://lucene3"));
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		//多条件查询
		BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
		QueryParser parser = new QueryParser("title", analyzer);
		Query query1 = parser.parse(q);
		
		QueryParser parser2 = new QueryParser("content", analyzer);
		Query query2 = parser2.parse(q);
		booleanQuery.add(query1, BooleanClause.Occur.SHOULD);
		booleanQuery.add(query2, BooleanClause.Occur.SHOULD);
		
		//单条件查询title
		BooleanQuery.Builder booleanQuery3 = new BooleanQuery.Builder();
		QueryParser parser3 = new QueryParser("title", analyzer);
		Query query3 = parser3.parse(q);
		booleanQuery3.add(query3, BooleanClause.Occur.SHOULD);
		
		//单条件查询content
		BooleanQuery.Builder booleanQuery4 = new BooleanQuery.Builder();
		QueryParser parser4 = new QueryParser("content", analyzer);
		Query query4 = parser4.parse(q);
		booleanQuery4.add(query4, BooleanClause.Occur.SHOULD);
		
		//得出结果topdocs类
		TopDocs docs = searcher.search(booleanQuery.build(), 1000);
		TopDocs docs1 = searcher.search(booleanQuery3.build(), 1000);
		TopDocs docs2 = searcher.search(booleanQuery4.build(), 1000);
		
		//得分
		QueryScorer scorer = new QueryScorer(query1);
		//代码高亮
		Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>", "</b></font>");
		Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
		highlighter.setTextFragmenter(fragmenter);
		
		for(ScoreDoc scoreDoc : docs.scoreDocs) {
			Document doc = searcher.doc(scoreDoc.doc);
			Blog blog = new Blog();
			blog.setId(Integer.parseInt(doc.get("id")));
			blog.setReleaseDateStr(doc.get("releaseDate"));
			String title = doc.get("title");
			String content = doc.get("content");
			
			if(title != null) {
				TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
				String hTitle = highlighter.getBestFragment(tokenStream, title);
				if(StringUtil.isEmpty(hTitle)) {
					blog.setTitle(title);
				}else {
					blog.setTitle(hTitle);
				}
			}
			if(content != null) {
				TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(content));
				String hContent = highlighter.getBestFragment(tokenStream, content);
				if(StringUtil.isEmpty(content)) {
					if(content.length() < 200) {
						blog.setContent(content);
					}else {
						blog.setContent(content.substring(0, 200));
					}
				}else {
					blog.setContent(hContent);
				}
			}
			blogList.add(blog);
		}
		if(blogList.size() == 0) {
			
			for(ScoreDoc scoreDoc : docs1.scoreDocs) {
				Document doc = searcher.doc(scoreDoc.doc);
				Blog blog = new Blog();
				blog.setId(Integer.parseInt(doc.get("id")));
				blog.setReleaseDateStr(doc.get("releaseDate"));
				String title = doc.get("title");
				String content = doc.get("content");
				if(title != null) {
					TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
					String hTitle = highlighter.getBestFragment(tokenStream, title);
					blog.setContent(content);
					if(StringUtil.isEmpty(hTitle)) {
						blog.setContent(content);
						blog.setTitle(title);
					}else {
						blog.setTitle(hTitle);
					}
				}
				blogList.add(blog);
			}
			for(ScoreDoc scoreDoc : docs2.scoreDocs) {
				Document doc = searcher.doc(scoreDoc.doc);
				Blog blog = new Blog();
				blog.setId(Integer.parseInt(doc.get("id")));
				String title = doc.get("title");
				String content = doc.get("content");
				blog.setReleaseDateStr(doc.get("releaseDate"));
				blog.setTitle(title);
				if(content != null) {
					TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(content));
					String hContent = highlighter.getBestFragment(tokenStream, content);
					if(StringUtil.isEmpty(content)) {
						if(content.length() < 200) {
							blog.setContent(content);
						}else {
							blog.setContent(content.substring(0, 200));
						}
					}else {
						blog.setContent(hContent);
					}
				}
				blogList.add(blog);
			}
		}
		return blogList;
	}
	/**
	 * 删除指定博客的索引
	 * @param blogId
	 * @throws Exception
	 */
	public void deleteIndex(String blogId) throws Exception{
		IndexWriter writer = getWriter();
		writer.deleteDocuments(new Term("id", blogId));
		writer.forceMergeDeletes();//强制删除
		writer.commit();
		writer.close();
	}
	/**
	 * 更新博客索引
	 * @param blog
	 * @throws Exception
	 */
	public void updateIndex(Blog blog ) throws Exception{
		IndexWriter writer = getWriter();
		Document doc = new Document();
		doc.add(new StringField("id", String.valueOf(blog.getId()), Field.Store.YES));
		doc.add(new TextField("title", blog.getTitle(), Field.Store.YES));
		doc.add(new StringField("releaseDate", DateUtil.formatDate(new Date(), "yyyy-MM-dd"), Field.Store.YES));
		doc.add(new TextField("content", blog.getContentNoTag(), Field.Store.YES));
		writer.updateDocument(new Term("id", String.valueOf(blog.getId())), doc);
		writer.close();
	}
}
