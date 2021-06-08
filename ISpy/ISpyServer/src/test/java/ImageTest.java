import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

// Test the getters and setters integral to the game
class ImageTest {
    private static Image info;
    private static File testFile;

    @BeforeAll
    static public void setUp() {
        info = new Image();
        testFile = new File("User/Desktop/CS 440/440-Group-15-Spring-2021/Code/ISpy/iSpyTestingPics/Neighborhood1/bumblebee.jpg");
    }

    // Test that the image's filename is correct
    @Test
    void imageFileTest() {
        info.setimageFile(testFile);
        assertEquals(info.getimageFile().getName(), "bumblebee.jpg");
    }

    // Test that the item name passed in is correct
    @Test
    void itemNameTest() {
        info.setItemName("Bumblebee");
        assertEquals(info.getItemName(), "Bumblebee");
    }
}