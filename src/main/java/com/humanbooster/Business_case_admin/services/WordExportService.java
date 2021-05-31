package com.humanbooster.Business_case_admin.services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import com.humanbooster.Business_case_admin.model.Answer;
import com.humanbooster.Business_case_admin.model.Media;
import com.humanbooster.Business_case_admin.model.Question;
import com.humanbooster.Business_case_admin.model.TechnicalTest;
import com.humanbooster.Business_case_admin.model.TestQuestion;

@Service
public class WordExportService {
		
	public static String output = "src/main/resources/static/word/techTest-export.docx";
	
	public void generatorWord(TechnicalTest techTest) throws IOException {
		
		XWPFDocument document = new XWPFDocument();
		
		Set<TestQuestion> testQuestions = techTest.getTestQuestions();
		
		XWPFParagraph TechTextName = document.createParagraph();
		TechTextName.setAlignment(ParagraphAlignment.BOTH.CENTER);
		
		XWPFRun TechTextNameRun = TechTextName.createRun();
		TechTextNameRun.setText(techTest.getNom());
		TechTextNameRun.setBold(false);
		TechTextNameRun.setColor("000000");
//		TechTextNameRun.setFontFamily("Courier");
		TechTextNameRun.setFontSize(20);
		
		
		int i = 1;
		
		for(TestQuestion tq : testQuestions) {
			
			Question q = tq.getQuestion();
			
			XWPFParagraph questionNo = document.createParagraph();
			questionNo.setAlignment(ParagraphAlignment.BOTH.LEFT);
			
			XWPFRun questionNoRun = questionNo.createRun();
			questionNoRun.setText("Question n°" + i);
			questionNoRun.setBold(true);
			questionNoRun.setColor("000000");
//			questionNoRun.setFontFamily("Courier");
			questionNoRun.setFontSize(16);
			
			i++;
			
			XWPFParagraph questionText = document.createParagraph();
			questionText.setAlignment(ParagraphAlignment.BOTH.LEFT);
			
			XWPFRun questionTextRun = questionText.createRun();
			questionTextRun.setText(q.getText());
			questionTextRun.setColor("000000");
			questionTextRun.setFontSize(12);
			
			if(q.getMedia() != null) {
				Media media = q.getMedia();
				if(Objects.equals(media.getMediaType(), "video")) {
					XWPFParagraph questionVideo = document.createParagraph();
					questionVideo.setAlignment(ParagraphAlignment.BOTH.LEFT);
					
					XWPFRun questionVideotRun = questionVideo.createRun();
					questionVideotRun.setText("contenu video");
					questionVideotRun.setColor("000000");
					questionVideotRun.setFontSize(12);
				} else if (Objects.equals(media.getMediaType(), "image")) {
					XWPFParagraph questionImage = document.createParagraph();
					questionImage.setAlignment(ParagraphAlignment.BOTH.LEFT);
					
					XWPFRun questionImagetRun = questionImage.createRun();
					questionImagetRun.setText("contenu image");
					questionImagetRun.setColor("000000");
				}
			}
			
			List<Answer> answers = q.getAnswers();
			
			int j = 1;
			for(Answer a : answers) {
				
				XWPFParagraph answerNo = document.createParagraph();
				answerNo.setAlignment(ParagraphAlignment.BOTH.LEFT);
				
				XWPFRun answerNoRun = answerNo.createRun();
				answerNoRun.setText("Réponse n°" + j);
				answerNoRun.setBold(true);
				answerNoRun.setColor("000000");
				answerNoRun.setFontSize(14);
				
				j++;
				
				XWPFParagraph answerText = document.createParagraph();
				answerText.setAlignment(ParagraphAlignment.BOTH.LEFT);
				
				XWPFRun answerTextRun = answerText.createRun();
				answerTextRun.setText(a.getText());
				answerTextRun.setColor("000000");
				answerTextRun.setFontSize(12);
				
				if(a.getMedia() != null) {
					Media media = a.getMedia();
					if(Objects.equals(media.getMediaType(), "video")) {
						XWPFParagraph answerVideo = document.createParagraph();
						answerVideo.setAlignment(ParagraphAlignment.BOTH.LEFT);
						
						XWPFRun answerVideotRun = answerVideo.createRun();
						answerVideotRun.setText("contenu video");
						answerVideotRun.setColor("000000");
						answerVideotRun.setFontSize(12);
					} else if (Objects.equals(media.getMediaType(), "image")) {
						XWPFParagraph answerImage = document.createParagraph();
						answerImage.setAlignment(ParagraphAlignment.BOTH.LEFT);
						
						XWPFRun answerImagetRun = answerImage.createRun();
						InputStream pic = new FileInputStream("src/main/resources/static/uploads/" + media.getFileName());
//						byte [] picbytes = IOUtils.toByteArray(pic);
						try {
							answerImagetRun.addPicture(pic, XWPFDocument.PICTURE_TYPE_PNG, "src/main/resources/static/uploads/" + media.getFileName(),300,300);
							System.out.println("src/main/resources/static/uploads/" + media.getFileName());
						} catch (InvalidFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				}			
			}
		
			
		}
		
		FileOutputStream out = new FileOutputStream(output);
		document.write(out);
		out.close();
		document.close();
	}
}
