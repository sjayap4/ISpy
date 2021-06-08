import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

// AwsConnection: Image rekognition API class
public class AwsConnection {
    // Image and String variables
    Image imageSent;
    String formatInfo;

    // AwsConnection constructor
    AwsConnection(Image info, String format){
        imageSent = info;
        formatInfo = format;
    }

    // checkConnection function
    public Boolean checkConnection() throws IOException {
        AWSCredentials awsCreds = new BasicAWSCredentials(
                "AKIARBM2542RBRWMC37U",
                "/iTMEd0UPUP4YCENFfyKnmY3y8QB7qfvipk+pDqB");

        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard()
                .withRegion("us-west-2")
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();

        DetectLabelsRequest request = new DetectLabelsRequest()
                .withImage(new com.amazonaws.services.rekognition.model.Image().withBytes(imageToByteBuffer(imageSent.getimageFile(),formatInfo)))
                .withMaxLabels(10).withMinConfidence(75F);
        try {
            DetectLabelsResult result = rekognitionClient.detectLabels(request);
            List<Label> labels = result.getLabels();

            //go through the labels
            for (Label label : labels) {

                //checks whether image is the one we are looking for
                if(label.getName().equals( imageSent.getItemName())){
                    return true;
                }
            }
            return false;
        } catch (AmazonRekognitionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ByteBuffer imageToByteBuffer(File imagePath, String format) throws IOException {
        BufferedImage originalImage = ImageIO.read(imagePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( originalImage, format, baos );
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        ByteBuffer buf = ByteBuffer.wrap(imageInByte);
        return buf;
    }
}

