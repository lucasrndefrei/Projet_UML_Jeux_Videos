package fr.efrei.domain;


import java.io.Serializable;
import java.util.Objects;

public class Game implements Serializable {
    private final String id;
    private final String title;
    private final String genre;
    private final GamePlatform platform;
    private final GameType type;
    private final double price;
    private boolean available;

    private Game(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.genre = builder.genre;
        this.platform = builder.platform;
        this.available = builder.available;
        this.type = builder.type;
        this.price = builder.price;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public GamePlatform getPlatform() { return platform; }
    public boolean isAvailable() { return available; }
    public GameType getType() { return type; }
    public double getPrice() { return price; }

    public void setAvailable(boolean available) { this.available = available; }

    public static class Builder {
        private String id;
        private String title;
        private String genre;
        private GamePlatform platform;
        private boolean available = true;
        private GameType type = GameType.RENTAL;
        private double price = 0.0;

        public Builder setId(String id) { this.id = id; return this; }
        public Builder setTitle(String title) { this.title = title; return this; }
        public Builder setGenre(String genre) { this.genre = genre; return this; }
        public Builder setPlatform(GamePlatform platform) { this.platform = platform; return this; }
        public Builder setAvailable(boolean available) { this.available = available; return this; }
        public Builder setType(GameType type) { this.type = type; return this; }
        public Builder setPrice(double price) { this.price = price; return this; }

        public Game build() {
            return new Game(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;
        Game game = (Game) o;
        return Objects.equals(id, game.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", platform=" + platform +
                ", type=" + type +
                ", price=" + price +
                ", available=" + available +
                '}';
    }
}
