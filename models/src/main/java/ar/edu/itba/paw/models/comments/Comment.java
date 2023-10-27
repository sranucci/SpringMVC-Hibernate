package ar.edu.itba.paw.models.comments;

import ar.edu.itba.paw.models.recipe.Recipe;
import ar.edu.itba.paw.models.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "tbl_comment_comment_id_seq")
    @SequenceGenerator(sequenceName = "tbl_comment_comment_id_seq",name = "tbl_comment_comment_id_seq",allocationSize = 1)
    @Column(name = "comment_id")
    private long commentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipe_id",nullable = false)
    private Recipe recipe;


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(name = "comment_content",nullable = false)
    private String commentContent;

    @Column(name = "created_at",nullable = false)
    private Date createdAt;


    @OneToMany(mappedBy = "comment",fetch = FetchType.LAZY,orphanRemoval = true)
    private List<CommentLikeData> commentLikeData;

    @Formula("(SELECT count(*) FROM tbl_comment_like_dislike t WHERE t.comment_id = comment_id AND t.is_like = true)")
    private long commentLikes;

    @Formula("(SELECT count(*) FROM tbl_comment_like_dislike t WHERE t.comment_id = comment_id AND t.is_like = false)")
    private long commentDislikes;



    public Comment(String commentContent, User user, Recipe recipe, Date createdAt ){
        this.user = user;
        this.commentContent = commentContent;
        this.recipe = recipe;
        this.createdAt = createdAt;
    }



    // por cada comentario --> recorro toda la lista de data de ese comentario --> si esta likeado lo marco
    public boolean userLikes(long userId){
        return commentLikeData.stream().anyMatch( commentData -> commentData.isLiked() && commentData.getUser().getId() == userId);
    }

    public boolean userDislikes(long userId){
        return  commentLikeData.stream().anyMatch( commentData -> !commentData.isLiked() && commentData.getUser().getId() == userId);
    }



}
