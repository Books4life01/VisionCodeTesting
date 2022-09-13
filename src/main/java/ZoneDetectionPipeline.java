
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Point;
import org.opencv.core.Rect;


import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import java.awt.Rectangle;
import org.firstinspires.ftc.robotcore.external.Telemetry;


public class ZoneDetectionPipeline extends OpenCvPipeline {
    //Upeer and lower HSV Values, use HSV Helper to convert
    public Scalar lower = new Scalar(12 , 93, 0);
    public Scalar upper = new Scalar(20 ,255,255);
    //Will hold black and white result
    public Mat filtered = new Mat();
    //holds subMat
    public Mat detectionArea = new Mat();
    Telemetry telemetry = null;

    public Rect subMat;
    //CREATE all Mats as instance variables so you dont forget to call .release(0 on therm)

    public ZoneDetectionPipeline(Telemetry in){
        telemetry = in;
    }
    @Override
    public Mat processFrame(Mat input) {
        
        //Convert Color to HSV better for diffreintiating colors
        Imgproc.cvtColor(input, input, Imgproc.COLOR_RGB2HSV);
        
        //turns everyhing not in the range of colors to black
        Core.inRange(input, lower, upper, filtered);

        //define searching area REc(x,y,width,height)
        subMat = new Rect(100,100,100,50);
        //create submat to search in
        detectionArea = filtered.submat(subMat);

        //Sum the elements
        Scalar sum = Core.sumElems(detectionArea);
        //Sum the ammount should be 255*number of white pixels
        Double num = Double.valueOf(String.valueOf(sum).split(",",0)[0].replace("[",""));
        //total of pixels in the area
        double area = subMat.width*subMat.height;
        //Calculate how much percent of Detection Area is white
        double numPixelsWhite = (num/255.0);
        //divide nums pixels by total pixels
        double percent = round((numPixelsWhite / area), 3);
        //For Visualization
        Imgproc.rectangle(filtered, new Point(subMat.x,subMat.y), new Point(subMat.x+subMat.width, subMat.y+subMat.height), new Scalar(255, 255, 255), 1);

        telemetry.addLine("Num"+ numPixelsWhite);
        telemetry.addLine("Total Pixels"+ area);
        telemetry.addLine("Percent"+ percent*100);
        telemetry.update();
        return filtered;
    }
    public double round(double in, int place){
        int cast = (int)(in * (Math.pow(10,place)));
        return (double)cast/Math.pow(10,place);
    }
    //Notes
    //Core.bitwise_and(input, input, mask, filtered);
    //Imgproc.cvtColor(mask, mask, Imgproc.COLOR_HSV2RGB);
    //Imgproc.threshold(input, input, 76, 255, Imgproc.THRESH_BINARY);




}