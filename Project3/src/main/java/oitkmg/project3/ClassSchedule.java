package oitkmg.project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Used to hold all Fitness Classes for all
 * gym locations.
 * @author Kevin George, Omar Talaat
 */
public class ClassSchedule {
    /**
     * Array of all fitness classes.
     */
    private FitnessClass [] classes;
    /**
     * Amount of fitness classes.
     */
    private int numClasses;

    /**
     * Default constructor for ClassSchedule.
     */
    public ClassSchedule() {

    }

    /**
     * Load fitness class schedule from the fitness class schedule file.
     */
    public void loadClassSchedule(){
        if(numClasses != 0) return;
        Scanner s;
        try {
            s = new Scanner(new File("src/main/classSchedule.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while(s.hasNextLine()){
            numClasses++;
            s.nextLine();
        }
        this.classes = new FitnessClass[numClasses];
        try {
            s = new Scanner(new File("src/main/classSchedule.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while(s.hasNextLine()){
            String[] inputs = s.nextLine().split(" ");
            addClass(new FitnessClass(inputs[0], inputs[1],
                    inputs[2], Location.valueOf(inputs[3].toUpperCase())));
        }
    }

    /**
     * Adds a fitness class to the class schedule.
     * @param fitnessClass FitnessClass to be added.
     */
    public void addClass(FitnessClass fitnessClass){
        for(int i = 0; i < numClasses; ++i){
            if(classes[i] == null){
                classes[i] = fitnessClass;
                return;
            }
        }
    }

    /**
     * Checks if the class name exists for any gym.
     * @param newClass FitnessClass representing the
     *                 class name to be searched for.
     * @return true if the class name exists; false otherwise.
     */
    public boolean isValidClass(FitnessClass newClass){
        for(FitnessClass fitnessClass : classes){
            if(fitnessClass.getClassName().
                    equalsIgnoreCase(newClass.getClassName()))
                return true;
        }
        return false;
    }

    /**
     * Checks if instructor exists for any gym.
     * @param newClass FitnessClass representing the
     *                 instructor to be searched for.
     * @return true if the instructor exists; false otherwise.
     */
    public boolean isValidInstructor(FitnessClass newClass){
        for(FitnessClass fitnessClass : classes){
            if(fitnessClass.getInstructor().
                    equalsIgnoreCase(newClass.getInstructor()))
                return true;
        }
        return false;
    }

    /**
     * Checks if a specific class exists in the schedule.
     * @param newClass FitnessClass representing the class
     *                 to be searched for.
     * @return true if class name, instructor, and location are the
     * same; false otherwise.
     */
    public FitnessClass exists(FitnessClass newClass){
        for(FitnessClass fitnessClass : classes){
            if(fitnessClass.getClassName().
                    equalsIgnoreCase(newClass.getClassName())
                    && fitnessClass.getInstructor().
                    equalsIgnoreCase(newClass.getInstructor())
                    && fitnessClass.getLocation().getTown().
                    equalsIgnoreCase(newClass.getLocation().getTown()))
                return fitnessClass;
        }
        return null;
    }

    /**
     * Gets the list of fitness classes.
     * @return FitnessClass array of all fitness classes.
     */
    public FitnessClass[] getClasses(){
        return classes;
    }

    /**
     * Gets the number of classes in the schedule.
     * @return an integer representing the number of classes
     * in the schedule.
     */
    public int getNumClasses(){
        return numClasses;
    }
}