package nl.hu.bep3.order.Aplication.response;

public class ReviewResponseDTO {
    private Long id;
    public int rating;
    public String message;

  public ReviewResponseDTO(int rating, String message) {
    this.rating = rating;
    this.message = message;
  }

  public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
