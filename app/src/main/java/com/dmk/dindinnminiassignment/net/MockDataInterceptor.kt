package com.dmk.dindinnminiassignment.net

import android.util.Log
import com.dmk.dindinnminiassignment.BuildConfig
import okhttp3.*
import java.io.IOException

class MockDataInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var response: Response? = null
        if (BuildConfig.DEBUG) {
            var responseString = "invalid path"
            // Get Request URI.
            val uri = chain.request().url().uri()
            // Get Query String.
            val query = uri.query

            // Parse the Query String.
            if (uri.path.equals("/categories/", ignoreCase = true)) {
                responseString = CATEGORIES
            } else if (uri.path.equals("/category/", ignoreCase = true)) {
                val parsedQuery = query.split("=").toTypedArray()
                Log.v("FAKE", "parsed category id=" + parsedQuery[1])
                responseString =
                    if (parsedQuery[0].equals("id", ignoreCase = true) && parsedQuery[1].equals(
                            "1",
                            ignoreCase = true
                        )
                    ) {
                        CATEGORY_MENU_PIZZA
                    } else if (parsedQuery[0].equals(
                            "id",
                            ignoreCase = true
                        ) && parsedQuery[1].equals(
                            "2",
                            ignoreCase = true
                        )
                    ) {
                        CATEGORY_MENU_SUSHI
                    } else {
                        CATEGORY_MENU_DRINKS
                    }
            }
            response = Response.Builder()
                .code(200)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(
                    ResponseBody.create(
                        MediaType.parse("application/json"),
                        responseString.toByteArray()
                    )
                )
                .addHeader("content-type", "application/json")
                .build()
        } else {
            response = chain.proceed(chain.request())
        }
        return response
    }

    companion object {
        // Mock RESPONSES.
        private const val CATEGORIES =
            "[{\"id\":0,\"name\":\"Pizza\"},{\"id\":1,\"name\":\"Susshi\"},{\"id\":2,\"name\":\"Drinks\"}]"
        private const val CATEGORY_MENU_PIZZA =
            "[ {\"id\":1,\"foodName\":\"Hawaiaan\",\"ingredients\":\"Mozarrela,BBQ Sauce\" ,\"price\":344.90,\"imgUrl\":\"\"}," +
                    "{\"id\":1,\"foodName\":\"Classic \",\"ingredients\":\"Mozarrela,BBQ Sauce\" ,\"price\":230.00,\"imgUrl\":\"\"},{\"id\":1,\"foodName\":\"Burgers\",\"ingredients\":\"Mozarrela,BBQ Sauce\" ,\"price\":92.00,\"imgUrl\":\"\"}]"
        private const val CATEGORY_MENU_SUSHI =
            "[ {\"id\":1,\"foodName\":\"Sushi Curry\",\"ingredients\":\"Mozarrela,BBQ Sauce\" ,\"price\":344.90}," +
                    "{\"id\":1,\"foodName\":\"Prawns \",\"ingredients\":\"Mozarrela,BBQ Sauce\" ,\"price\":200.00,\"imgUrl\":\"\"},{\"id\":1,\"foodName\":\"See Food Classic\",\"ingredients\":\"Mozarrela,BBQ Sauce\" ,\"price\":344.00,\"imgUrl\":\"\"}]"
        private const val CATEGORY_MENU_DRINKS =
            "[ {\"id\":1,\"foodName\":\"Cocacola \",\"ingredients\":\"Orange,Pinneaple\" ,\"price\":344.90,\"imgUrl\":\"\"}," +
                    "{\"id\":1,\"foodName\":\"Pepsi \",\"ingredients\":\"Orange,Pinneaple\" ,\"price\":190.00,\"imgUrl\":\"\"},{\"id\":1,\"foodName\":\"See Food Classic\",\"ingredients\":\"Special Flavour\" ,\"price\":50.00,\"imgUrl\":\"\"}]"
    }
}