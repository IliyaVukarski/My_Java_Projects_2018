package article;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainForum {
	
	public static void main(String[] args) throws IOException {
		List<String> topics = new ArrayList<String>();
		topics.add("Mountains");
		topics.add("Series");
		topics.add("News");
		List<UserCredentials> userCredentialsRepo = new ArrayList<UserCredentials>();
		UserCredentials credentials = null;
		List<Article> articleMountains = new ArrayList<Article>();
		List<Article> articleSeries = new ArrayList<Article>();
		List<Article> articleNews = new ArrayList<Article>();
		Map<String, List<Article>> articles = new HashMap<>();
		articles.put(topics.get(0), articleMountains);
		articles.put(topics.get(1), articleSeries);
		articles.put(topics.get(2), articleNews);
		List<String> commands = new ArrayList<String>();
		commands.add("End");
		commands.add("Register");
		commands.add("Choose topic to post article");
		commands.add("Choose article to post comment");
		commands.add("Print all articles with comments");
		commands.add("Delete article");
		commands.add("Delete comment");
		commands.add("Edit article");
		commands.add("Edit comment");
		
		while(true) {
			Scanner scanner = new Scanner(System.in);
			Article article = new Article();
			System.out.println("Welcome to our forum");
			System.out.println("Please choose one of the following commands:");
			System.out.println("Register, Choose topic to post article, Choose article to post comment, "
					+ "Print all articles with comments, Delete article, Delete comment, "
					+ "Edit article, Edit comment, END");
			String[] userInputCommand = scanner.next().split(" ");
			if(userInputCommand[0].equalsIgnoreCase(commands.get(0))) {
				break;
			}
			for(String com : userInputCommand) {
				if(com.equalsIgnoreCase(commands.get(1))) {
					System.out.println("Enter type of user Regular user or Administrator. ");
					String type = scanner.next();
					if(type.equalsIgnoreCase("RegularUser")) {
						System.out.println("Enter username and password");
						credentials = new RegularUser(scanner.next(), scanner.next());
						credentials.addNewUser(userCredentialsRepo, credentials);
					}else if(type.equalsIgnoreCase("Administrator")) {
						credentials = new AdministratorUser(scanner.next(), scanner.next());
						credentials.addNewUser(userCredentialsRepo, credentials);
					}else {
						System.out.println("Error wrong input! ");
					}
				}else if(com.equalsIgnoreCase(commands.get(2))) {
					System.out.println("Enter username and password");
					credentials.setUsername(scanner.next());
					credentials.setPassword(scanner.next());
					if(credentials.checkIfUserExistsInTheSystem(userCredentialsRepo, credentials)) {
						article.setUserPosting(credentials);
						System.out.println("Choose topic for the article Mountains, Series, News. ");
						String articleTopic = scanner.next();
						article.setArticleTopic(articleTopic);
						System.out.println("Enter article name. ");
						String articleTitle = scanner.next();
						article.setArticleTitle(articleTitle);
						System.out.println("Enter article content. ");
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
						if(articleTopic.equalsIgnoreCase(topics.get(0))) {
							article.postArticle(articleMountains, article, bufferedReader);
						}else if(articleTopic.equalsIgnoreCase(topics.get(1))) {
							article.postArticle(articleSeries, article, bufferedReader);
						}else if(articleTopic.equalsIgnoreCase(topics.get(2))) {
							article.postArticle(articleNews, article, bufferedReader);
						}else {
							System.out.println("No such topic in the system. ");
						}
					}else {
						System.out.println("No such user in the system. ");
					}
				}else if(com.equalsIgnoreCase(commands.get(3))) {
					System.out.println("Enter username and password. ");
					credentials.setUsername(scanner.next());
					credentials.setPassword(scanner.next());
					if(credentials.checkIfUserExistsInTheSystem(userCredentialsRepo, credentials)) {
						article.printAllArticles(articles);
						System.out.println("Choose topic for the article. ");
						String articleTopic = scanner.next();
						System.out.println("Enter article title that you wish to put a comment. ");
						String articleTitle = scanner.next();
						for(Map.Entry<String, List<Article>> entry : articles.entrySet()) {
							List<Article> allArticles = entry.getValue();
							int numberOfComments = 0;
							for(Article currentArticle : allArticles) {
								if(currentArticle.getArticleTitle().contains(articleTitle)) {
									numberOfComments++;
									String numberOfComment = String.valueOf(numberOfComments);
									System.out.println("Enter article comment");
									BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
									if(articleTopic.equalsIgnoreCase(topics.get(0))) {
										article.postComment(articleMountains, articleTitle, numberOfComment, credentials, bufferedReader);
									}else if(articleTopic.equalsIgnoreCase(topics.get(1))) {
										article.postComment(articleSeries, articleTitle, numberOfComment, credentials, bufferedReader);
									}else if(articleTopic.equalsIgnoreCase(topics.get(2))) {
										article.postComment(articleNews, articleTitle, numberOfComment, credentials, bufferedReader);
									}else {
										System.out.println("No such topic. ");
									}
								}else {
									System.out.println("No such article exists. ");
								}
							}
						}
					}else {
						System.out.println("No such user exists. ");
					}
				}else if(com.equalsIgnoreCase(commands.get(4))) {
					System.out.println("Enter username and password. ");
					credentials.setUsername(scanner.next());
					credentials.setPassword(scanner.next());
					if(credentials.checkIfUserExistsInTheSystem(userCredentialsRepo, credentials)) {
						article.printAllArticles(articles);
						article.printAllComments(articles);
					}
				}else if(com.equalsIgnoreCase(commands.get(5))) {
					System.out.println("Enter username and password. ");
					credentials.setUsername(scanner.next());
					credentials.setPassword(scanner.next());
					System.out.println("Enter article you wish to delete. ");
					String articleTitle = scanner.next();
					article.deleteArticle(userCredentialsRepo, articles, credentials, articleTitle);
				}else if(com.equalsIgnoreCase(commands.get(6))) {
					System.out.println("Enter username and password. ");
					credentials.setUsername(scanner.next());
					credentials.setPassword(scanner.next());
					System.out.println("Enter article topic. ");
					String articleTopic = scanner.next();
					System.out.println("Enter article title that you wish to delete a comment. ");
					String articleTitle = scanner.next();
					System.out.println("Enter number of comment that you wish to delete. ");
					String numberOfComment = scanner.next();
					article.deleteComment(userCredentialsRepo, articles, credentials, articleTopic, articleTitle, numberOfComment);
				}else if(com.equalsIgnoreCase(commands.get(7))) {
					System.out.println("Enter username and password. ");
					credentials.setUsername(scanner.next());
					credentials.setPassword(scanner.next());
					System.out.println("Enter article name that you wish to edit. ");
					String articleTitle = scanner.next();
					System.out.println("Enter article content. ");
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
					List<String> articleContent = new ArrayList<>();
					String readFromConsole = "";
					do {
						readFromConsole = bufferedReader.readLine();
						articleContent.add(readFromConsole);
					}while(!readFromConsole.equalsIgnoreCase("Stop"));
					article.editArticle(userCredentialsRepo, articles, articleContent, credentials, articleTitle);
				}else if(com.equalsIgnoreCase(commands.get(8))) {
					System.out.println("Enter username and password. ");
					credentials.setUsername(scanner.next());
					credentials.setPassword(scanner.next());
					System.out.println("Enter article topic. ");
					String articleTopic = scanner.next();
					System.out.println("Enter article title that you wish to edit a comment. ");
					String articleTitle = scanner.next();
					System.out.println("Enter number of comment that you wish to edit. ");
					String numberOfComment = scanner.next();
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
					List<String> userComment = new ArrayList<>();
					String readFromConsole = "";
					do {
						readFromConsole = bufferedReader.readLine();
						userComment.add(readFromConsole);
					}while(!readFromConsole.equalsIgnoreCase("Stop"));
					article.editComment(userCredentialsRepo, articles, credentials, userComment, articleTopic, articleTitle, numberOfComment);
				}else {
					System.out.println("Wrong input! ");
				}
			}
		}
	}
}