package javaproject.chat.server;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;


public class CommandManagerV2 implements CommandManager{


    @Getter
    private ArrayList<String> badWords = new ArrayList<>();


    public CommandManagerV2() { //욕설 파일
        loadBadWordsFile("src/badwords.txt");

    }
    //    private void loadBadWordsFile(String filename) { src->main->resource 해결못함(나중에)
//        try (BufferedReader reader = new BufferedReader(
//                new InputStreamReader(
//                        Objects.requireNonNull(CommandManagerV2.class.getClassLoader()
//                                .getResourceAsStream(filename))))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                addBadWord(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    private void loadBadWordsFile(String filename) { //욕설 파일 읽기
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                addBadWord(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  void addBadWord(String line){
        if(line != null &&!line.trim().isEmpty()&&!badWords.contains(line)){
            badWords.add(line);
        }
    }
    public boolean checkBadWord(String recevied ){
        for(String bw: badWords ){
            if(recevied.contains(bw)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int execute(String totalMessage, Session session) throws IOException {

        if (totalMessage.startsWith("/exit")){
            return  1;
        }else if(totalMessage.startsWith("/강퇴")){
            return 2;
        }else if(checkBadWord(totalMessage)){
            return 3;
        }

        return 0;
    }
}


