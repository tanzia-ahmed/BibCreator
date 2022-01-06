/*
 * @author Ahmed-40166924
 * */
public class FileInvalidException extends Exception{
    public FileInvalidException(){
        super("Error: Input file cannot be parsed due to missing information");
    }
    public FileInvalidException(String message){
        super(message);
    }
}
