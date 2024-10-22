package code.climb.cccars.dto;

import jakarta.validation.constraints.NotNull;

public class CommentDTO {
    @NotNull
    private Integer listing;

    private Integer replyTo;

    @NotNull
    private String content;

    private Boolean isApproved = false;

    public @NotNull Integer getListing() {
        return listing;
    }

    public Integer getReplyTo() {
        return replyTo;
    }

    public @NotNull String getContent() {
        return content;
    }

    public Boolean getApproved() {
        return isApproved;
    }
}
