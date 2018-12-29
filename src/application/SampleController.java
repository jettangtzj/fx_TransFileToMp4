package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

public class SampleController{
	
	@FXML
	private TextArea textarea;
	
	@FXML
	private Button button_choose;
	
	@FXML
	private Button button_change;
	
	@FXML
	private TextField url_inputfield;
	
	private List<File> subfiles;//目录下的文件
	
	public SampleController() {
		
	}

	@FXML
	public void initialize() {
		
	}
	
	
	@FXML
	private void chooseDir(ActionEvent event) {
		DirectoryChooser dc = new DirectoryChooser();
		dc.setInitialDirectory(new File(System.getProperty("user.home") + "/AppData/Local/Google/Chrome/User Data/Default/Media Cache"));
		File chosenDir = dc.showDialog(((Node)event.getSource()).getScene().getWindow());
		if (chosenDir != null) {
			String chosenDirpath = chosenDir.getAbsolutePath();
			System.out.println(chosenDirpath);
			url_inputfield.setText(chosenDirpath);
			getFilesInPath(chosenDirpath);
		} else {
			System.out.println("没有选择文件夹");
			Alert alert = new Alert(AlertType.WARNING, "请选择一个文件夹", ButtonType.YES);
			alert.show();
		}
	}
	
	
	public void getFilesInPath(String path) {
		appendText("读取文件夹下的所有文件=====");
		subfiles = new ArrayList<File>();
		File dir = new File(path);
		File[] files = dir.listFiles();
		for(File f : files){
			if(!f.isDirectory() && f.getName().startsWith("f") && !f.getName().endsWith("mp4")) {
				subfiles.add(f);
				appendText(f.getName());
			}
		}
		appendText("读取完毕=====");
	}
	
	public void appendText(String str) {
		if(textarea.getText().equals("")) {
			showText(str);
		}else {
			textarea.setText(textarea.getText() +  str + "\n");
		}
	}
	
	public void showText(String str) {
		textarea.setText(str + "\n");
	}
	
	
	
	
	@FXML
	private void beginChange(ActionEvent event) {
		appendText("开始转换=====");
		if(subfiles == null || subfiles.size() == 0) {
			Alert alert = new Alert(AlertType.WARNING, "没有文件进行转换", ButtonType.YES);
			alert.show();
			return;
		}
		try {
			for(File f : subfiles){
				String filename = f.getName();
				String newFileName = "";
				if(filename.indexOf(".") == -1) {
					newFileName = filename + ".mp4";
				}else {
					newFileName = filename.substring(0, filename.lastIndexOf(".")) + ".mp4";
				}
				
				File newFile = new File(f.getParent(), newFileName);
				if(f.renameTo(newFile)) {
					appendText("转换文件： "+ filename + " 新文件：" + newFileName + "成功");
				}else {
					appendText("转换文件： "+ filename + " 新文件：" + newFileName + "失败");
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		appendText("转换结束=====");
	}
	
}
