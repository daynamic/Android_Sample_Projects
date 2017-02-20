package com.test.asharm93.spiderg1;

/**
 * Created by asharm93 on 5/5/2016.
 */
public class SentDocumentModel {
    String ImageName,rfqsntname,rfqsntsender,Gladimage,Gladtechname,gladfourthline,docnumb,rfq,timstamp,image1,image2,image3;
    public SentDocumentModel(String ImageName,String rfqsntname,String rfqsntsender, String Gladimage,String Gladtechname ,String gladfourthline , String docnumb, String rfq,String timstamp, String image1, String image2,String image3) {
        super();
        this.ImageName=ImageName;
        this.rfqsntname = rfqsntname;
        this.rfqsntsender=rfqsntsender;
        this.Gladimage = Gladimage;
        this.Gladtechname = Gladtechname;
        this.gladfourthline= gladfourthline;
        this.docnumb= docnumb;
        this.rfq= rfq;
        this.timstamp=timstamp;
        this.image1=image1;
        this.image2=image2;
        this.image3=image3;
  }

    public String getImageName() {
        return this.ImageName;
    }
    public String getrfqsntname() {
        return this.rfqsntname;
    }
    public String getrfqsntsender(){
        return this.rfqsntsender;
    }
    public String getGladimage() {
        return this.Gladimage;
    }
    public String getGladtechname() {
        return this.Gladtechname;
    }
    public String getgladfourthline()
    {
        return this.gladfourthline;
    }
    public String getdocnumb()
    {
        return this.docnumb;
    }

    public String getrfq()
    {
        return this.rfq;
    }
    public String gettimstamp()
    {
        return this.timstamp;
    }
    public String getimage1()
    {
        return this.image1;
    }
    public String getimage2()
    {
        return this.image2;
    }
    public String getimage3()
    {
        return this.image3;
    }
    public String toString()
    {
        return rfqsntname;
    }
}
