package app.summarease.model.entities.system;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class defines the schema of the response. It is used to encapsulate data prepared by
 * the server side, this object will be serialized to JSON before sent back to the client end.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Result {
    private boolean flag; // Two values: true means success, false means not success

    private Integer code; // Status code. e.g., 200

    private String message; // Response message

    private Object data; // The response payload


    /**
     * Dataless constructor
     * @param flag the flag of the result.
     * @param code The code of the result.
     * @param message The response message.
     */
    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }


}
