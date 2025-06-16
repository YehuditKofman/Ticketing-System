import ClientServer.InquiryManagerActions;
import ClientServer.RequestData;
import ClientServer.ResponseData;
import ClientServer.ResponseStatus;
import Data.Complaint;
import Data.Inquiry;
import Data.Question;
import Data.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class InquiryManagerClient {
    Scanner scanner=new Scanner(System.in);
   private Socket connectToServer;
   private ObjectOutputStream out;
    private ObjectInputStream in ;
    public InquiryManagerClient() throws IOException {
       connectToServer=new Socket("127.0.0.1",5000);
        in = new ObjectInputStream(connectToServer.getInputStream());
        out = new ObjectOutputStream(connectToServer.getOutputStream());
   }

    public Socket getConnectToServer() {
        return connectToServer;
    }
    public void execut() throws IOException {
       int choice=0;
        RequestData r;
        ResponseData rs;
      do{
      System.out.println("to view all the inquris press 1 ,to add inqury press 2,to see all inquiry in month press 3,to cancel" +
              "inquiry press 4,to exit press 5");
      choice= scanner.nextInt();
      switch(choice){
          case(1):r=new RequestData(InquiryManagerActions.ALL_INQUIRY,null);
                  sendToServer(r);
                  rs=readFromServer();
                  printResponseData(rs);
                  break;
          case (2):
                 Inquiry i=creatInquiry();
                 r=new RequestData(InquiryManagerActions.ADD_INQUIRY,i);
                 sendToServer(r);
                 rs=readFromServer();
                 printResponseData(rs);
                 break;
          case(3):
                int month=chooseMonth();
                int year=chooseyear();
                r=new RequestData(InquiryManagerActions.MONTHLY_INQUIRIES,year,month);
                sendToServer(r);
                rs=readFromServer();
                printResponseData(rs);
                break;
          case(4):
              int code=chooseCodeInquiryToCancel();
              r=new RequestData(InquiryManagerActions.CANCEL,code);
              sendToServer(r);
              rs=readFromServer();
              printResponseData(rs);
              break;
          case(5): r=new RequestData(InquiryManagerActions.EXIT);
                   sendToServer(r);
                   break;

      }
      }while(choice!=3);
      connectToServer.close();
      in.close();
      out.close();
    }
    public void  sendToServer(RequestData r) throws IOException {
        out.writeObject(r);
       //out.close();
    }
    public ResponseData readFromServer() {
       ResponseData r;
       try {
           r= (ResponseData) in.readObject();
       }
       catch(IOException e){
           e.printStackTrace();
            r=new ResponseData(ResponseStatus.FAIL,"IOExeption",null);

       } catch (ClassNotFoundException e) {
           e.printStackTrace();
            r=new ResponseData(ResponseStatus.FAIL,"ClassNotFoundException",null);
       }

      return r;
    }
    public void printResponseData(ResponseData r){
       System.out.println("the status is:"+r.getStatus());
       System.out.println("the message is:"+r.getMessage());
       if(r.getResult()!=null)
      System.out.println("the result is:"+r.getResult().toString());
    }
    public Inquiry creatInquiry(){
        int num=-1;
        Inquiry inquiry=null;
        System.out.println("for question enter 1,for request enter 2,for complaint enter 3 ");
        num=scanner.nextInt();
        switch(num){
            case 1:inquiry=new Question();
                break;

            case 2:inquiry=new Request();
                break;
            case 3:inquiry=new Complaint();
                break;
            default:num=-1;

        }
        inquiry.fillDataByUser();
        return  inquiry;

    }
    public int chooseMonth(){
        System.out.println("enter num of month to get him inquiries");
        int month= scanner.nextInt();
        return month;
    }
    public int chooseyear(){
        System.out.println("enter num of year to get him inquiries");
        int year= scanner.nextInt();
        return year;
    }
    public Integer chooseCodeInquiryToCancel(){
        System.out.println("enter inquiry code to cancel");
        Integer code=scanner.nextInt();
        return code;
    }

}
