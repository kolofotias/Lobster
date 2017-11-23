package net.jesterland.lobster;

/**
 * Created by jester on 2/1/2017.
 */

public class MovieInfo {

        private String overview;
        private String rating;
        private String release_date;
        private String image;
        private String title;

        public MovieInfo() {
                super();
        }

        public String getImage() {
                return image;
        }

        public void setImage(String image) {
                this.image = image;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getRating() {
                return rating;
        }

        public void set_rating(String rating) {
                this.rating = rating;
        }

        public String getOverview() {
                return overview;
        }

        public void setOverview(String overview) {
                this.overview = overview;
        }

        public String getRelease_date() {
                return release_date;
        }

        public void setRelease_date(String release_date) {
                this.release_date = release_date;
        }

}



