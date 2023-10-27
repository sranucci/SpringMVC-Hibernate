package ar.edu.itba.paw.models.comments;

import ar.edu.itba.paw.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;



@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_comment_like_dislike")
public class CommentLikeData {

    @EmbeddedId
    private CommentLikeId id;

    @Column(name = "is_like")
    private boolean liked;



    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("commentId")
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "comment_id")
    private Comment comment;



    public CommentLikeData(Comment comment, User user, boolean liked){
        this.id = new CommentLikeId(user.getId(),comment.getCommentId());
        this.liked = liked;
        this.user = user;
        this.comment = comment;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Embeddable
    public static class CommentLikeId implements Serializable{
        private long userId;
        private long commentId;
    }


}
