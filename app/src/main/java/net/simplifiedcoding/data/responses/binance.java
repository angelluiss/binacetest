package net.simplifiedcoding.data.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class binance {

        @SerializedName("kind")
        @Expose
        private String kind;


        @SerializedName("totalItems")
        @Expose
        int totalItems;

        public String getKind() {
            return kind;
        }

        public int getTotalItems() {
            return totalItems;
        }

}
