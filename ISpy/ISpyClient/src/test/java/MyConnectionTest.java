import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyConnectionTest {
    private static MyConnection connection;
    private static HashMap<String,String> park1Items;
    private static HashMap<String,String> park2Items;
    private static HashMap<String,String> park3Items;
    private static HashMap<String,String> park4Items;
    private static HashMap<String,String> park5Items;
    private static HashMap<String,String> neighborhood1Items;
    private static HashMap<String,String> neighborhood2Items;
    private static HashMap<String,String> neighborhood3Items;
    private static HashMap<String,String> neighborhood4Items;
    private static HashMap<String,String> neighborhood5Items;
    private static HashMap<String,String> zoo1Items;
    private static HashMap<String,String> zoo2Items;
    private static HashMap<String,String> zoo3Items;
    private static HashMap<String,String> zoo4Items;
    private static HashMap<String,String> zoo5Items;
    private static HashMap<String, String> testResults;

    @BeforeAll
    static public void setUp() {
        connection = new MyConnection();
        park1Items = new HashMap<>();
        park2Items = new HashMap<>();
        park3Items = new HashMap<>();
        park4Items = new HashMap<>();
        park5Items = new HashMap<>();
        neighborhood1Items = new HashMap<>();
        neighborhood2Items = new HashMap<>();
        neighborhood3Items = new HashMap<>();
        neighborhood4Items = new HashMap<>();
        neighborhood5Items = new HashMap<>();
        zoo1Items = new HashMap<>();
        zoo2Items = new HashMap<>();
        zoo3Items = new HashMap<>();
        zoo4Items = new HashMap<>();
        zoo5Items = new HashMap<>();
        testResults = new HashMap<>();

        try {
            connection.connect();
        }catch(FileNotFoundException fileNotFoundException){
            fileNotFoundException.printStackTrace();
        }
    }

    @BeforeEach
    void init() {
        park1Items.clear();
        park2Items.clear();
        park3Items.clear();
        park4Items.clear();
        park5Items.clear();
        neighborhood1Items.clear();
        neighborhood2Items.clear();
        neighborhood3Items.clear();
        neighborhood4Items.clear();
        neighborhood5Items.clear();
        zoo1Items.clear();
        zoo2Items.clear();
        zoo3Items.clear();
        zoo4Items.clear();
        zoo5Items.clear();
        testResults.clear();
    }

    @Test
    void park1ItemsTest() {
        park1Items.put("Bench", "Hint: A really long chair that can fit a lot of people");
        park1Items.put("Ball", "Hint: Something used in a lot of games. Usually people throw and catch them.");
        park1Items.put("Bicycle", "Hint: Something you could ride that has two wheels and a seat");
        park1Items.put("Bucket", "Hint: A tool you can use to hold sand");
        park1Items.put("Rock", "Hint: A hard object found on the ground that could be round or sharp");
        park1Items.put("Cloud", "Hint: The poofy white stuff in the sky");
        park1Items.put("Drinking Fountain", "Hint: Some place where people can drink water");
        park1Items.put("Bumblebee", "Hint: A big, fuzzy yellow insect with black stripes that takes pollen and buzzes");
        park1Items.put("Moon", "Hint: A round shape of light that appears at night in the sky");
        park1Items.put("Grass", "Hint: The green stuff on the ground");

        connection.clear();
        connection.getAreaAndLevel(1, "Park");
        connection.getItems();
        testResults = connection.getResults();

        assertTrue(park1Items.equals(testResults));
    }

    @Test
    void neighborhood1ItemsTest(){
        neighborhood1Items.put("House", "Hint: Something that people live in");
        neighborhood1Items.put("Car", "Hint: Something that grownups drive to get to places");
        neighborhood1Items.put("Fence", "Hint: A thing made out of wood that keep things out");
        neighborhood1Items.put("Sidewalk", "Hint: The concrete that you walk on to stay safe from the road");
        neighborhood1Items.put("Rock", "Hint: A hard object found on the ground that could be round or sharp");
        neighborhood1Items.put("Cloud", "Hint: The poofy white stuff in the sky");
        neighborhood1Items.put("Drinking Fountain", "Hint: Some place where people can drink water");
        neighborhood1Items.put("Bumblebee", "Hint: A big, fuzzy yellow insect with black stripes that takes pollen and buzzes");
        neighborhood1Items.put("Moon", "Hint: A round shape of light that appears at night in the sky");
        neighborhood1Items.put("Grass", "Hint: The green stuff on the ground");
        neighborhood1Items.put("Stopsign", "Hint: A red hexagon sign on the sidewalk");

        connection.clear();
        connection.getAreaAndLevel(1, "Neighborhood");
        connection.getItems();
        testResults = connection.getResults();

        assertTrue(neighborhood1Items.equals(testResults));

    }

    @Test
    void zoo1ItemsTest(){
        zoo1Items.put("Zebra", "Hint: An animal with black and white stripes and looks like a horse");
        zoo1Items.put("Monkey", "Hint: An animal that is small, climbs trees and eats bananas");
        zoo1Items.put("Tiger", "Hint: An animal with orange, white and black stripes");
        zoo1Items.put("Rock", "Hint: A hard object found on the ground that could be round or sharp");
        zoo1Items.put("Cloud", "Hint: The poofy white stuff in the sky");
        zoo1Items.put("Drinking Fountain", "Hint: Some place where people can drink water");
        zoo1Items.put("Bumblebee", "Hint: A big, fuzzy yellow insect with black stripes that takes pollen and buzzes");
        zoo1Items.put("Moon", "Hint: A round shape of light that appears at night in the sky");
        zoo1Items.put("Grass", "Hint: The green stuff on the ground");
        zoo1Items.put("Turtle", "Hint: An animal that lives in a big shell and walks really slow");
        zoo1Items.put("Fish", "Hint: An animal that lives in the water and has fins");


        connection.clear();
        connection.getAreaAndLevel(1, "Zoo");
        connection.getItems();
        testResults = connection.getResults();

        assertTrue(zoo1Items.equals(testResults));

    }

    @Test
    void park2ItemsTest(){
        park2Items.put("Slide", "Hint: When you go on a slide, you start from the top of the playground and go down to the ground");
        park2Items.put("Sand", "Hint: Really small minerals and rocks");
        park2Items.put("Swing", "Hint: Something near a playground that you sit on and move back and forth");
        park2Items.put("Shovel", "Hint: A tool you can use to scoop up sand");
        park2Items.put("Statue", "Hint: A figure of a person");
        park2Items.put("Starry Sky", "Hint: Small dots of light in the sky at night when it’s dark");
        park2Items.put("Squirrel", "Hint: An animal with a bushy tail, likes to climb trees and eat nuts");
        park2Items.put("Flower", "Hint: A plant that has petals and pretty colors");
        park2Items.put("Tree", "Hint: A tall plant that has green leaves");

        connection.clear();
        connection.getAreaAndLevel(2, "Park");
        connection.getItems();
        testResults = connection.getResults();

        assertTrue(park2Items.equals(testResults));



    }


    @Test
    void neighborhood2ItemsTest() {
        neighborhood2Items.put("Garage", "Hint: A place where all the cars and tools are");
        neighborhood2Items.put("Dog", "Hint: An animal that barks and is everyone’s best friend");
        neighborhood2Items.put("Window", "Hint: A thing on the side of a house that you could look through to see outside/inside");
        neighborhood2Items.put("Door", "Hint: A thing that you walk through to go from one room to another");
        neighborhood2Items.put("Ant", "Hint: An insect on the ground that’s black, small and loves sweets");
        neighborhood2Items.put("Statue", "Hint: A figure of a person");
        neighborhood2Items.put("Starry Sky", "Hint: Small dots of light in the sky at night when it’s dark");
        neighborhood2Items.put("Squirrel", "Hint: An animal with a bushy tail, likes to climb trees and eat nuts");
        neighborhood2Items.put("Flower", "Hint: A plant that has petals and pretty colors");
        neighborhood2Items.put("Tree", "Hint: A tall plant that has green leaves");

        connection.clear();
        connection.getAreaAndLevel(2, "Neighborhood");
        connection.getItems();
        testResults = connection.getResults();

        assertTrue(neighborhood2Items.equals(testResults));
    }

    @Test
    void zoo2ItemsTest() {
        zoo2Items.put("Lion", "Hint: An animal that roars and has a mane");
        zoo2Items.put("Giraffe", "Hint: An animal that has a really long neck");
        zoo2Items.put("Snake", "Hint: A reptile that is slimy and doesn’t have legs");
        zoo2Items.put("Elephant", "Hint: An animal that stomps when it walks, has two tusks and big ears");
        zoo2Items.put("Statue", "Hint: A figure of a person");
        zoo2Items.put("Starry Sky", "Hint: Small dots of light in the sky at night when it’s dark");
        zoo2Items.put("Squirrel", "Hint: An animal with a bushy tail, likes to climb trees and eat nuts");
        zoo2Items.put("Flower", "Hint: A plant that has petals and pretty colors");
        zoo2Items.put("Tree", "Hint: A tall plant that has green leaves");

        connection.clear();
        connection.getAreaAndLevel(2, "Zoo");
        connection.getItems();
        testResults = connection.getResults();

        assertTrue(zoo2Items.equals(testResults));
    }

    @Test
    void park3ItemsTest() {
        park3Items.put("Tennis Court", "Hint: Has a net with small green balls and rackets");
        park3Items.put("Fountain", "Hint: A spray of water created by a machine, or the structure from which the water flows");
        park3Items.put("Bird", "Hint: An animal that chirps and flies");
        park3Items.put("Rabbit", "Hint: An animal that hops and runs really fast");
        park3Items.put("Sun", "Hint: A bright light in the sky during the day");
        park3Items.put("Playground", "Hint: Something that a lot of kids play on");
        park3Items.put("Basketball Court", "Hint: Has a tall hoop with a net and a big round ball that bounces");
        park3Items.put("Trash Can", "Hint: A bin that you throw stuff away in");
        park3Items.put("Toy", "Hint: An item that kids play with");
        park3Items.put("Leaf", "Hint: The stuff that falls from trees in the fall");


        connection.clear();
        connection.getAreaAndLevel(3, "Park");
        connection.getItems();
        testResults = connection.getResults();

        assertTrue(park3Items.equals(testResults));
    }

    @Test
    void neighborhood3ItemsTest() {
        neighborhood3Items.put("Mailbox", "Hint: The place where mail goes");
        neighborhood3Items.put("Tree House", "Hint: A small house up in the trees in backyards");
        neighborhood3Items.put("Cat", "Hint: An animal that meows and purrs");
        neighborhood3Items.put("Garden", "Hint: A place in the backyard where there are plants and flowers");
        neighborhood3Items.put("Lamp Post", "Hint: Something that lights up the street when it gets dark");
        neighborhood3Items.put("Sun", "Hint: A bright light in the sky during the day");
        neighborhood3Items.put("Leaf", "Hint: The stuff that falls from trees in the fall");
        neighborhood3Items.put("Fountain", "Hint: A spray of water created by a machine, or the structure from which the water flows");
        neighborhood3Items.put("Rabbit", "Hint: An animal that hops and runs really fast");
        neighborhood3Items.put("Bird", "Hint: An animal that chirps and flies");
        neighborhood3Items.put("Trash Can", "Hint: A bin that you throw stuff away in");


        connection.clear();
        connection.getAreaAndLevel(3, "Neighborhood");
        connection.getItems();
        testResults = connection.getResults();

        assertTrue(neighborhood3Items.equals(testResults));
    }

    @Test
    void zoo3ItemsTest() {
        zoo3Items.put("Giant Panda", "Hint: A black and white bear");
        zoo3Items.put("Penguin", "Hint: A bird that doesn’t fly and lives in the cold");
        zoo3Items.put("Dolphin", "Hint: An animal that swims and likes to jump up in the air");
        zoo3Items.put("Koala", "Hint: An animal that is small and gray and likes to climb up trees");
        zoo3Items.put("Duck", "Hint: An animal that quacks and floats on the water");
        zoo3Items.put("Sun", "Hint: A bright light in the sky during the day");
        zoo3Items.put("Leaf", "Hint: The stuff that falls from trees in the fall");
        zoo3Items.put("Fountain", "Hint: A spray of water created by a machine, or the structure from which the water flows");
        zoo3Items.put("Rabbit", "Hint: An animal that hops and runs really fast");
        zoo3Items.put("Bird", "Hint: An animal that chirps and flies");
        zoo3Items.put("Trash Can", "Hint: A bin that you throw stuff away in");


        connection.clear();
        connection.getAreaAndLevel(3, "Zoo");
        connection.getItems();
        testResults = connection.getResults();

        assertTrue(zoo3Items.equals(testResults));
    }

    @Test
    void park4ItemsTest() {
        park4Items.put("Picnic", "Hint: A meal you could eat outside");
        park4Items.put("Skateboard", "Hint: Something that you could ride with 4 wheels and stand on");
        park4Items.put("Scooter", "Hint: Something that you could ride with 2 wheels and stand on");
        park4Items.put("Tree Stump", "Hint: The part left when the tree is cut off");
        park4Items.put("Pond", "Hint: A small body of water found in zoos, parks and neighborhoods");
        park4Items.put("Grasshopper", "Hint: A green insect that hops around");
        park4Items.put("Table", "Hint: Something that is flat and people like to sit around");
        park4Items.put("Chair", "Hint: Something that people sit on");
        park4Items.put("Butterfly", "Hint: An insect that can fly and has colorful wings after it hatches from its cocoon");
        park4Items.put("Bird Nest", "Hint: Home of birds, usually on a tree");


        connection.clear();
        connection.getAreaAndLevel(4, "Park");
        connection.getItems();
        testResults = connection.getResults();

        assertTrue(park4Items.equals(testResults));
    }

    @Test
    void neighborhood4ItemsTest() {
        neighborhood4Items.put("Dog House", "Hint: A small house in backyards where a dog stays and sleeps.");
        neighborhood4Items.put("Toolshed", "Hint: A place where all tools go");
        neighborhood4Items.put("Motorcycle", "Hint: A two wheel car that makes a loud noise when it passes by");
        neighborhood4Items.put("Fire Hydrant", "Hint: A red item on the sidewalk that firefighters use to get water");
        neighborhood4Items.put("Pond", "Hint: A small body of water found in zoos, parks and neighborhoods");
        neighborhood4Items.put("Grasshopper", "Hint: A green insect that hops around");
        neighborhood4Items.put("Table", "Hint: Something that is flat and people like to sit around");
        neighborhood4Items.put("Chair", "Hint: Something that people sit on");
        neighborhood4Items.put("Butterfly", "Hint: An insect that can fly and has colorful wings after it hatches from its cocoon");
        neighborhood4Items.put("Bird Nest", "Hint: Home of birds, usually on a tree");


        connection.clear();
        connection.getAreaAndLevel(4, "Neighborhood");
        connection.getItems();
        testResults = connection.getResults();

        assertTrue(neighborhood4Items.equals(testResults));
    }

    @Test
    void zoo4ItemsTest() {
        zoo4Items.put("Polar Bear", "Hint: A white bear that lives in the cold");
        zoo4Items.put("Hippo", "Hint: A big animal with a big mouth and small ears. It walks on all fours and can walk on land and swim");
        zoo4Items.put("Gorilla", "Hint: An animal that can stand on its two arms and two legs but can also sit like us");
        zoo4Items.put("Kangaroo", "Hint: An animal that likes to hop and has a pouch where its babies stay");
        zoo4Items.put("Otter", "Hint: An animal with brown fur and whiskers and likes to swim");
        zoo4Items.put("Lemur", "Hint: A little animal with black eyes and striped tail");
        zoo4Items.put("Pond", "Hint: A small body of water found in zoos, parks and neighborhoods");
        zoo4Items.put("Grasshopper", "Hint: A green insect that hops around");
        zoo4Items.put("Table", "Hint: Something that is flat and people like to sit around");
        zoo4Items.put("Chair", "Hint: Something that people sit on");
        zoo4Items.put("Butterfly", "Hint: An insect that can fly and has colorful wings after it hatches from its cocoon");
        zoo4Items.put("Bird Nest", "Hint: Home of birds, usually on a tree");


        connection.clear();
        connection.getAreaAndLevel(4, "Zoo");
        connection.getItems();
        testResults = connection.getResults();

        assertTrue(zoo4Items.equals(testResults));
    }

    @Test
    void park5ItemsTest() {
        park5Items.put("Daisy", "Hint: A flower with a yellow middle and white petals");
        park5Items.put("Pine", "Hint: A triangle tree that can be used as a Christmas tree");
        park5Items.put("Kite", "Hint: A colorful toy that can be flown in the sky");
        park5Items.put("Soccer Ball", "Hint: A black and white ball");
        park5Items.put("Racket", "Hint: Something you use to play tennis to hit the ball");
        park5Items.put("Baseball", "Hint: A small white ball");
        park5Items.put("Frisbee", "Hint: A disk people throw in the air");
        park5Items.put("Firefly", "Hint: A bug that glows when it is dark outside");
        park5Items.put("Bush", "Hint: A green plant that has lots of leaves");
        park5Items.put("Dandelion", "Hint: A small yellow flowers");
        park5Items.put("Puddle", "Hint: Little holes in the ground full of water when it rains");


        connection.clear();
        connection.getAreaAndLevel(5, "Park");
        connection.getItems();
        testResults = connection.getResults();

        assertTrue(park5Items.equals(testResults));
    }

    @Test
    void neighborhood5ItemsTest() {
        neighborhood5Items.put("Tulip", "Hint: A flower that has triangle shaped petals, usually found in gardens");
        neighborhood5Items.put("Rose", "Hint: A red flower, usually found in gardens");
        neighborhood5Items.put("Roof", "Hint: Triangle shaped items that covers the tops of houses");
        neighborhood5Items.put("Trampoline", "Hint: Something shaped as a circle that you can jump on");
        neighborhood5Items.put("Pool", "Hint: Something filled with water that you can swim in");
        neighborhood5Items.put("Lawn Mower", "Hint: Something that you use to cut grass with");
        neighborhood5Items.put("Firefly", "Hint: A bug that glows when it is dark outside");
        neighborhood5Items.put("Bush", "Hint: A green plant that has lots of leaves");
        neighborhood5Items.put("Brick", "Hint: A reddish-brown object that is used to build walls");
        neighborhood5Items.put("Dandelion", "Hint: A small yellow flowers");
        neighborhood5Items.put("Puddle", "Hint: Little holes in the ground full of water when it rains");


        connection.clear();
        connection.getAreaAndLevel(5, "Neighborhood");
        connection.getItems();
        testResults = connection.getResults();

        assertTrue(neighborhood5Items.equals(testResults));
    }

    @Test
    void zoo5ItemsTest() {
        zoo5Items.put("Pond Lily", "Hint: A leaf found in the water that frogs like to stay on");
        zoo5Items.put("Frog", "Hint: A green slimy animal with big eyes");
        zoo5Items.put("Rhino", "Hint: A big gray animal that walks on four feet and has a big horn on the front of its face");
        zoo5Items.put("Bear", "Hint: A big brown furry animal");
        zoo5Items.put("Cheetah", "Hint: A yellow animal with black spots, a long tail, and can run fast");
        zoo5Items.put("Armadillo", "Hint: An animal that has a hard shell and can turn into a ball");
        zoo5Items.put("Camel", "Hint: A tall animal that has a hump on its back");
        zoo5Items.put("Flamingo", "Hint: A pink bird that stands in water with its really long skinny legs");
        zoo5Items.put("Crocodile", "Hint: A big reptile with a big mouth and has lots of teeth");
        zoo5Items.put("Puddle", "Hint: Little holes in the ground full of water when it rains");
        zoo5Items.put("Firefly", "Hint: A bug that glows when it is dark outside");
        zoo5Items.put("Bush", "Hint: A green plant that has lots of leaves");
        zoo5Items.put("Dandelion", "Hint: A small yellow flowers");


        connection.clear();
        connection.getAreaAndLevel(5, "Zoo");
        connection.getItems();
        testResults = connection.getResults();

        assertTrue(zoo5Items.equals(testResults));
    }

}