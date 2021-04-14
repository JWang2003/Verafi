package com.example.misinformation;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

//https://www.youtube.com/watch?v=ZJepo2wRiBk
public class DataAccess {

    private Context context;

    public DataAccess(Context context) {
        this.context = context;
    }

    // Example: lessonName is Introduction,
    public ArrayList<Section> getLessonSections(String lessonName) {
        ArrayList<Section> sections = new ArrayList<Section>();
        try {
            JSONObject jsonObject = new JSONObject(JsonLessonFromAsset());
            JSONArray jsonAllSections = jsonObject.getJSONArray(lessonName);

            for (int i = 0; i < jsonAllSections.length(); i++) {
                JSONObject jsonSection = jsonAllSections.getJSONObject(i);
                String type = jsonSection.getString("type");
                if (type.equals("lesson")) {
                    Section section = new Section(type, jsonSection.getString("name"), jsonSection.getJSONObject("content"));
                    sections.add(section);
                } else if (type.equals("quiz")) {
                    Section section = new Section(type, jsonSection.getString("name"), jsonSection.getJSONObject("content"), jsonSection.getJSONArray("choices"), jsonSection.getJSONArray("correct"));
                    sections.add(section);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sections;
    }

    public ArrayList<Unit> getUnits() {
        ArrayList<Unit> units = new ArrayList<Unit>();
        try {
            JSONObject jsonObject = new JSONObject(JsonUnitFromAsset());
            JSONArray jsonUnits = jsonObject.getJSONArray("Units");
            for (int i = 0; i < jsonUnits.length(); i++) {
                JSONObject userData = jsonUnits.getJSONObject(i);
                Unit unit = new Unit(userData.getString("id"), userData.getString("name"), userData.getInt("progress"), userData.getJSONArray("lessons"));
                units.add(unit);
        }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return units;
    }

    private String JsonUnitFromAsset() {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open("units.json");
            int sizeOfFile = inputStream.available();
            byte[] bufferData = new byte[sizeOfFile];
            inputStream.read(bufferData);
            inputStream.close();
            json = new String(bufferData, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    private String JsonLessonFromAsset() {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open("lessons.json");
            int sizeOfFile = inputStream.available();
            byte[] bufferData = new byte[sizeOfFile];
            inputStream.read(bufferData);
            inputStream.close();
            json = new String(bufferData, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
