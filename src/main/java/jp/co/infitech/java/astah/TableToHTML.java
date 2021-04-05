package jp.co.infitech.java.astah;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.change_vision.jude.api.inf.model.IEREntity;
import com.change_vision.jude.api.inf.model.IERModel;
import com.change_vision.jude.api.inf.model.INamedElement;

public class TableToHTML {

    public static void makeHtml(INamedElement[] element,IERModel ermodel, File outputDirectory, boolean domain) {
        Path path = Paths.get(outputDirectory.toString(), "index.html");
        System.out.println("出力 : " + path);

        List<INamedElement> elementList = new ArrayList<INamedElement>();
        for(INamedElement ine : element) {
            elementList.add(ine);
        }
        Collections.sort(elementList, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                IEREntity en1 = (IEREntity)o1;
                IEREntity en2 = (IEREntity)o2;
                return en1.getPhysicalName().toLowerCase().compareTo(en2.getPhysicalName().toLowerCase());
            }
        });

        List<String> html = new ArrayList<>();
        html.add("<!DOCTYPE html>");
        html.add("<html>");
        html.add("<head>");
        html.add("<meta charset=\"UTF-8\">");
        html.add("<title>" + HtmlWord.getWord("entity_list") + "</title>");
        html.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"./index.css\">");
        html.add("</head>");
        html.add("<body>");
        html.add("<h1>" + HtmlWord.getWord("entity_list") + "</h1>");
        html.add("<table>");
        html.add("<tr><th>" + HtmlWord.getWord("model_name") + "</th><td>" + ermodel.getName() + "</td></tr>");
        html.add("<tr><th>" + HtmlWord.getWord("definition") + "</th><td>" + (ermodel.getDefinition() != null ? ermodel.getDefinition().replaceAll("\\r\\n|\\r|\\n", "<br />") : "")  + "</td></tr>");
        html.add("</table>");
        html.add("<br>");
        html.add("<table>");
        html.add("<tr><th>" + HtmlWord.getWord("logical_name") + "</th><th>" + HtmlWord.getWord("physical_name") + "</th><th>" + HtmlWord.getWord("type") + "</th><th>" + HtmlWord.getWord("definition") + "</th></tr>");
        for(INamedElement ine : elementList) {
            IEREntity entity= (IEREntity)ine;
            StringBuilder line = new StringBuilder();
            line.append("<tr><td>");
            line.append(entity.getLogicalName());
            line.append("</td><td>");
            line.append("<a href = \"./entity/" + entity.getPhysicalName() + ".html\">" + entity.getPhysicalName() + "</a>");
            line.append("</td><td>");
            line.append(entity.getType());
            line.append("</td><td>");
            line.append(entity.getDefinition() != null ? entity.getDefinition().replaceAll("\\r\\n|\\r|\\n", "<br />") : "");
            line.append("</td></tr>");
            html.add(line.toString());
        }
        html.add("</table>");
        if(domain == true) {
            html.add("<br>");
            html.add("<br>");
            html.add("<a href = \"./domain.html\">to " + HtmlWord.getWord("domain_list") + "</a>");
        }
        html.add("</body>");
        html.add("</html>");

        try {
            Files.write(path, html, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
