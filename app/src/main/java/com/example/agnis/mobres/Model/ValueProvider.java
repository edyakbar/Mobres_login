package com.example.agnis.mobres.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rizzabas on 02/09/2018.
 */

public class ValueProvider {
        @SerializedName("status")
        @Expose
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @SerializedName("result")
        @Expose
        private ResultProvider[] result;

        public ResultProvider[] getResult() {
            return result;
        }
}
