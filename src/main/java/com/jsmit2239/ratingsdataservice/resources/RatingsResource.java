package com.jsmit2239.ratingsdataservice.resources;

import com.jsmit2239.ratingsdataservice.models.Rating;
import com.jsmit2239.ratingsdataservice.models.UserRatings;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {

    @RequestMapping("/users/{userId}")
    public UserRatings getUsersRatings(@PathVariable("userId") String userId) throws IOException, ParseException {

        return new UserRatings(getRatingsFromJson(userId));
    }

    private List<Rating> getRatingsFromJson(String userId) throws IOException, ParseException {

        List<Rating> ratingsList = new ArrayList<>();
        File stubbedData = getFileFromResources("stubbedData/stubbedData.json");
        Object obj = new JSONParser().parse(new FileReader(stubbedData.getCanonicalPath()));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray jsonArray = (JSONArray) jsonObject.get("ratings");

        jsonArray.forEach(item -> {
            JSONObject object = (JSONObject) item;
            if (object.get("userId").equals(userId)) {
                Rating rating = new Rating(
                        object.get("userId").toString(),
                        object.get("movieId").toString(),
                        Integer.parseInt(object.get("rating").toString()));
                ratingsList.add(rating);
            }
        });

        return ratingsList;
    }

    private File getFileFromResources(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }
    }
}
