package article;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Article {
	private UserCredentials userPosting;
	private String articleTopic;
	private String articleTitle;
	private List<String> articleContent;
	private LocalDateTime timeOfPostingArticle;
	private List<PostOfArticle> postsOfArticle;
	
	public Article() {
		
	}
	
	public Article(UserCredentials userPosting, String articleTopic,
			String articleTitle, List<String> articleContent,
			List<PostOfArticle> postsOfArticle) {
		super();
		this.userPosting = userPosting;
		this.articleTopic = articleTopic;
		this.articleTitle = articleTitle;
		this.articleContent = articleContent;
		this.postsOfArticle = postsOfArticle;
	}
	
	public Article(UserCredentials userPosting, String articleTopic,
			String articleTitle, List<String> articleContent,
			LocalDateTime timeOfPostingArticle,
			List<PostOfArticle> postsOfArticle) {
		super();
		this.userPosting = userPosting;
		this.articleTopic = articleTopic;
		this.articleTitle = articleTitle;
		this.articleContent = articleContent;
		this.timeOfPostingArticle = timeOfPostingArticle;
		this.postsOfArticle = postsOfArticle;
	}
	
	public UserCredentials getUserPosting() {
		return userPosting;
	}

	public void setUserPosting(UserCredentials userPosting) {
		this.userPosting = userPosting;
	}

	public String getArticleTopic() {
		return articleTopic;
	}

	public void setArticleTopic(String articleTopic) {
		this.articleTopic = articleTopic;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public List<String> getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(List<String> articleContent) {
		this.articleContent = articleContent;
	}

	public LocalDateTime getTimeOfPostingArticle() {
		return timeOfPostingArticle;
	}

	public void setTimeOfPostingArticle(LocalDateTime timeOfPostingArticle) {
		this.timeOfPostingArticle = timeOfPostingArticle;
	}

	public List<PostOfArticle> getPostsOfArticle() {
		return postsOfArticle;
	}

	public void setPostsOfArticle(List<PostOfArticle> postsOfArticle) {
		this.postsOfArticle = postsOfArticle;
	}
	
	public void printArticleContent(List<Article> articles) {
		for(Article article : articles) {
			for(String word : article.getArticleContent()) {
				System.out.println(word + " ");
			}
			System.out.println();
			System.out.printf("The article has posted from %s.", article.getUserPosting().getUsername());
			System.out.println();
		}
	}
	
	public void printCommentContent(List<Article> articles) {
		for(Article article : articles) {
			for(String word : article.getArticleContent()) {
				System.out.println(word + " ");
			}
			System.out.println(article.getUserPosting().getUsername() + " ");
		}
		for(Article article : articles) {
			for(PostOfArticle postOfArticle : article.getPostsOfArticle()) {
				System.out.println("Comment number: " + postOfArticle.getNumberOfComment());
				for(String comment : postOfArticle.getUserComment()) {
					System.out.println(comment + " ");
				}
				System.out.println(article.getUserPosting().getUsername() + " ");
			}
		}
	}
	
	public void printAllArticles(Map<String, List<Article>> articles) {
		for(Map.Entry<String, List<Article>> entry : articles.entrySet()) {
			String key = entry.getKey().toString();
			List<Article> allArticles = entry.getValue();
			System.out.println(key);
			for(Article currentArticle : allArticles) {
				for(String word : currentArticle.getArticleContent()) {
					System.out.println(word + " ");
				}
			}
		}
	}
	
	public void printAllComments(Map<String, List<Article>> articles) {
		for(Map.Entry<String, List<Article>> entry : articles.entrySet()) {
			List<Article> allArticles = entry.getValue();
			for(Article currentArticle : allArticles) {
				for(PostOfArticle word : currentArticle.getPostsOfArticle()) {
					System.out.println(word.getUserComment() + " ");
					System.out.println(word.getNumberOfComment() + " " + word.getUserPostComment());
				}
			}
		}
	}
	
	public void editArticle(List<UserCredentials> userCredentialsRepo, Map<String, List<Article>> articles, List<String> articleContent, UserCredentials credentials, String articleTitle) {
		for(int index = 0; index < userCredentialsRepo.size(); index++) {
			if(userCredentialsRepo.get(index).getUsername().equalsIgnoreCase(credentials.getUsername()) &&
					userCredentialsRepo.get(index).getPassword().equalsIgnoreCase(credentials.getPassword())) {
				for(Map.Entry<String, List<Article>> entry : articles.entrySet()) {
					List<Article> allArticles = entry.getValue();
					for(int articleIndex = 0; articleIndex < allArticles.size(); index++) {
						if(allArticles.get(articleIndex).getArticleTitle().equalsIgnoreCase(articleTitle)) {
							if(allArticles.get(articleIndex).getArticleTitle().equalsIgnoreCase(articleTitle) && 
									allArticles.get(articleIndex).getUserPosting().getUsername().equalsIgnoreCase(credentials.getUsername()) || 
									allArticles.get(articleIndex).getArticleTitle().equalsIgnoreCase(articleTitle) && 
									credentials instanceof AdministratorUser) {
								allArticles.get(articleIndex).setArticleContent(articleContent);
								System.out.println("The comment was successfully edited. ");
							}else {
								System.out.println("Error you can't edit this post. ");
							}
						}else {
							System.out.println("This article doesn't exist. ");
						}
					}
				}
			}
		}
	}
	
	public void deleteArticle(List<UserCredentials> userCredentialsRepo, Map<String, List<Article>> articles, UserCredentials credentials, String articleTitle) {
		for(int index = 0; index < userCredentialsRepo.size(); index++) {
			if(userCredentialsRepo.get(index).getUsername().equalsIgnoreCase(credentials.getUsername()) &&
					userCredentialsRepo.get(index).getPassword().equalsIgnoreCase(credentials.getPassword())) {
				for(Map.Entry<String, List<Article>> entry : articles.entrySet()) {
					List<Article> allArticles = entry.getValue();
					for(int articleIndex = 0; articleIndex < allArticles.size(); articleIndex++) {
						if(allArticles.get(articleIndex).getArticleTitle().equalsIgnoreCase(articleTitle)) {
							if(allArticles.get(articleIndex).getArticleTitle().equalsIgnoreCase(articleTitle) && 
									allArticles.get(articleIndex).getUserPosting().getUsername().equalsIgnoreCase(credentials.getUsername()) ||
									allArticles.get(articleIndex).getArticleTitle().equalsIgnoreCase(articleTitle) && 
									credentials instanceof AdministratorUser) {
								allArticles.remove(articleIndex);
								System.out.println("Article was successfully deleted! ");
							}else {
								System.out.println("You can't delete this article! ");
							}
						}else {
							System.out.println("This article doesn't exist! ");
						}
					}
				}
			}
		}
	} 
	
	public void editComment(List<UserCredentials> userCredentialsRepo, Map<String, List<Article>> articles, UserCredentials credentials, List<String> userComment, String articleTopic, String articleTitle, String numberOfComment) {
		for(int index = 0; index < userCredentialsRepo.size(); index++) {
			if(userCredentialsRepo.get(index).getUsername().equalsIgnoreCase(credentials.getUsername()) &&
					userCredentialsRepo.get(index).getPassword().equalsIgnoreCase(credentials.getPassword())) {
				for(Map.Entry<String, List<Article>> entry : articles.entrySet()) {
					String key = entry.getKey();
					if(key.contains(articleTopic)) {
						List<Article> allArticles = entry.getValue();
						for(int articleIndex = 0; articleIndex < allArticles.size(); articleIndex++) {
							if(allArticles.get(articleIndex).getArticleTitle().equalsIgnoreCase(articleTitle)) {
								List<PostOfArticle> postOfArticles = allArticles.get(articleIndex).getPostsOfArticle();
								for(int postIndex = 0; postIndex < postOfArticles.size(); postIndex++) {
									if(postOfArticles.get(postIndex).getUserPostComment().getUsername().equalsIgnoreCase(credentials.getUsername()) &&
											postOfArticles.get(postIndex).getNumberOfComment().equalsIgnoreCase(numberOfComment)) {
										postOfArticles.get(postIndex).setUserComment(userComment);
										System.out.println("Edit successfull. ");
									}else {
										System.out.println("Post can't be edited! ");
									}
								}
							}else {
								System.out.println("No such article eists! ");
							}
						}
					}else {
						System.out.println("No such topic in database! ");
					}
				}
			}else {
				System.out.println("User doesn't exist in the system! ");
			}
		}
	}
	
	public void deleteComment(List<UserCredentials> userCredentialsRepo, Map<String, List<Article>> articles, UserCredentials credentials, String articleTopic, String articleTitle, String numberOfComment) {
		for(int index = 0; index < userCredentialsRepo.size(); index++) {
			if(userCredentialsRepo.get(index).getUsername().equalsIgnoreCase(credentials.getUsername()) &&
					userCredentialsRepo.get(index).getPassword().equalsIgnoreCase(credentials.getPassword())) {
				for(Map.Entry<String, List<Article>> entry : articles.entrySet()) {
					String key = entry.getKey();
					if(key.contains(articleTopic)) {
						List<Article> allArticles = entry.getValue();
						for(int articleIndex = 0; articleIndex < allArticles.size(); articleIndex++) {
							if(allArticles.get(articleIndex).getArticleTitle().equalsIgnoreCase(articleTitle)) {
								List<PostOfArticle> postOfArticles = allArticles.get(articleIndex).getPostsOfArticle();
								for(int postIndex = 0; postIndex < allArticles.size(); postIndex++) {
									if(postOfArticles.get(postIndex).getUserPostComment().getUsername().equalsIgnoreCase(credentials.getUsername()) &&
											postOfArticles.get(postIndex).getNumberOfComment().equalsIgnoreCase(numberOfComment)) {
										postOfArticles.remove(postIndex);
										System.out.println("This post was successfully deleted! ");
									}else {
										System.out.println("Post can't be deleted! ");
									}
								}
							}else {
								System.out.println("No such article exists! ");
							}
						}
					}else {
						System.out.println("No such topic in database! ");
					}
				}
			}else {
				System.out.println("User doesn't exist in the system. ");
			}
		}
 	}
	
	public void postArticle(List<Article> articles, Article article, BufferedReader bufferedReader) throws IOException {
		LocalDateTime timeOfPostingArticle = LocalDateTime.now();
		List<String> articleConntent = new ArrayList<String>();
		List<PostOfArticle> articleComments = new ArrayList<PostOfArticle>();
		String readFromConsole = "";
		articleConntent.add(article.getArticleTitle());
		do {
			readFromConsole = bufferedReader.readLine();
			articleConntent.add(readFromConsole);
		}while(!readFromConsole.equalsIgnoreCase("Stop"));
		article.setUserPosting(article.getUserPosting());
		article.setArticleTopic(getArticleTopic());
		article.setArticleTitle(article.getArticleTitle());
		article.setArticleContent(articleConntent);
		article.setTimeOfPostingArticle(timeOfPostingArticle);
		article.setPostsOfArticle(articleComments);
		articles.add(article);
		printArticleContent(articles);
	}
	
	public void postComment(List<Article> articles, String articleName, String numberOfComment, UserCredentials credentials, BufferedReader bufferedReader) throws IOException {
		LocalDateTime timeOFPostingComment = LocalDateTime.now();
		List<String> postContent = new ArrayList<>();
		String readFromConsole = "";
		for(int index = 0; index < articles.size(); index++) {
			if(articles.get(index).getArticleTitle().equalsIgnoreCase(articleName)) {
				do {
					readFromConsole = bufferedReader.readLine();
					postContent.add(readFromConsole);
				}while(!readFromConsole.equalsIgnoreCase("Stop"));
				articles.get(index).getPostsOfArticle().add(new PostOfArticle(credentials, timeOFPostingComment, postContent, numberOfComment));
			}else {
				System.out.println("No such article exists! ");
			}
		}
		printCommentContent(articles);
	}
}
