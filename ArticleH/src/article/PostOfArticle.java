package article;
import java.time.LocalDateTime;
import java.util.List;

public class PostOfArticle {
	private UserCredentials userPostComment;
	private LocalDateTime timeOfPostingComment;
	private List<String> userComment;
	private String numberOfComment;
	
	public PostOfArticle() {
		
	}
	
	public PostOfArticle(UserCredentials userPostComment,
			LocalDateTime timeOfPostingComment, List<String> userComment,
			String numberOfComment) {
		super();
		this.userPostComment = userPostComment;
		this.timeOfPostingComment = timeOfPostingComment;
		this.userComment = userComment;
		this.numberOfComment = numberOfComment;
	}
	
	public UserCredentials getUserPostComment() {
		return userPostComment;
	}

	public void setUserPostComment(UserCredentials userPostComment) {
		this.userPostComment = userPostComment;
	}

	public LocalDateTime getTimeOfPostingComment() {
		return timeOfPostingComment;
	}

	public void setTimeOfPostingComment(LocalDateTime timeOfPostingComment) {
		this.timeOfPostingComment = timeOfPostingComment;
	}

	public List<String> getUserComment() {
		return userComment;
	}

	public void setUserComment(List<String> userComment) {
		this.userComment = userComment;
	}

	public String getNumberOfComment() {
		return numberOfComment;
	}

	public void setNumnerOfComment(String numberOfComment) {
		this.numberOfComment = numberOfComment;
	}
}
