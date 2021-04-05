package jp.co.infitech.java.astah;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.change_vision.jude.api.inf.model.IERAttribute;
import com.change_vision.jude.api.inf.model.IEREntity;

public class EntityToHTML {
    public static void makeHtml(IEREntity entity, File outputDirectory, boolean domain) throws IOException {
        Path pathSub = Paths.get(outputDirectory.toString(), "entity");
        Path path = Paths.get(outputDirectory.toString(), "entity", entity.getPhysicalName() + ".html");
        System.out.println("出力 : " + path);

        if(Files.exists(pathSub) == false) {
            Files.createDirectory(pathSub);
        }

        List<String> html = new ArrayList<>();
        html.add("<!DOCTYPE html>");
        html.add("<html>");
        html.add("<head>");
        html.add("<meta charset=\"UTF-8\">");
        html.add("<title>" + entity.getLogicalName() + "/" + entity.getPhysicalName() + "</title>");
        html.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"../entity.css\">");
        html.add("</head>");
        html.add("<body>");
        html.add("<a href = \"../index.html\">to " + HtmlWord.getWord("entity_list") + "</a>");
        html.add("<br>");
        html.add("<br>");
        html.add("<h1>" + entity.getLogicalName() + "/" + entity.getPhysicalName() + "</h1>");
        html.add("<table>");

        String linetitle = "";
        linetitle = "<tr><th>" + HtmlWord.getWord("logical_name") +
                 "</th><th>" + HtmlWord.getWord("physical_name") +
                 (domain == true ? "</th><th>" + HtmlWord.getWord("domain_name") : "") +
                 "</th><th>" + HtmlWord.getWord("primary_key") +
                 "</th><th>" + HtmlWord.getWord("not_null") +
                 "</th><th>" + HtmlWord.getWord("data_type") +
                 "</th><th>" + HtmlWord.getWord("length_precision") +
                 "</th><th>" + HtmlWord.getWord("default_value") +
                 "</th><th>" + HtmlWord.getWord("definition") +
                 "</th></tr>";
        html.add(linetitle);
        for(IERAttribute attr : entity.getPrimaryKeys()) {
            html.add(makeAttributeHtml(attr, domain));
        }
        for(IERAttribute attr : entity.getNonPrimaryKeys()) {
            html.add(makeAttributeHtml(attr, domain));
        }
        html.add("</table>");
        html.add("<br>");
        html.add("<br>");
        html.add("<a href = \"../index.html\">to " + HtmlWord.getWord("entity_list") + "</a>");
        html.add("</body>");
        html.add("</html>");

        try {
            Files.write(path, html, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String makeAttributeHtml(IERAttribute attributes, boolean domain) {
        StringBuilder html = new StringBuilder();
        html.append("<tr><td>");
        html.append(attributes.getLogicalName() != null ? attributes.getLogicalName() : "");
        html.append("</td><td>");
        html.append(attributes.getPhysicalName() != null ? attributes.getPhysicalName() : "");
        html.append("</td><td>");
        if(domain == true) {
            html.append(attributes.getDomain() != null ? attributes.getDomain().getName() : "");
            html.append("</td><td>");
        }
        html.append(attributes.isPrimaryKey() ? "*" : "");
        html.append("</td><td>");
        html.append(attributes.isNotNull() ? "*" : "");
        html.append("</td><td>");
        html.append(attributes.getDatatype() != null ? attributes.getDatatype() : "");
        html.append("</td><td>");
        html.append(attributes.getLengthPrecision() != null ? attributes.getLengthPrecision() : "");
        html.append("</td><td>");
        html.append(attributes.getDefaultValue() != null ? attributes.getDefaultValue() : "");
        html.append("</td><td>");
        html.append(attributes.getDefinition() != null ? attributes.getDefinition().replaceAll("\\r\\n|\\r|\\n", "<br />") : "");
        html.append("</td></tr>");
        return html.toString();
    }

}
