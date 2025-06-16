package ClientServer;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class RequestData implements Serializable {
    private InquiryManagerActions action;
    private Object[] parameters;

    public Object[]  getParameters() {
        return parameters;
    }

    public void setAction(InquiryManagerActions action) {
        this.action = action;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public InquiryManagerActions getAction() {
        return action;
    }

    public RequestData(InquiryManagerActions action, Object...parameters) {
        this.action = action;
        if(parameters!=null) {
            int count = parameters.length;
            this.parameters = new Object[count];
            int i = 0;
            for (Object o : parameters) {
                this.parameters[i++] = o;
            }
        }

    }

}
