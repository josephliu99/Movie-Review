package persistance;

import org.json.JSONObject;

public interface Writable {

    // Method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns this as an JSON object
    JSONObject toJson();
}
