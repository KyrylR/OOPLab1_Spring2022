package ua.univ.utils;

import ua.univ.models.IDefaultModel;

import java.util.Map;

public class Utils {
    public static enum Role {
        MANAGER,
        DRIVER
    }
    public Utils() {
    }
    public static StringBuilder getSingleModelView(IDefaultModel model) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> map = model.getMap();

        stringBuilder.append("<table  class=\"w-auto table table-bordered table-sm\">\n<tbody>\n");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            stringBuilder.append("<tr>\n<td>").append(entry.getKey()).append("</td>\n")
                    .append("<td>").append(entry.getValue()).append("</td>\n</tr>\n");
        }

        stringBuilder.append("</tbody>\n</table>");

        return stringBuilder;
    }

    public static StringBuilder getTable(IDefaultModel[] models) {
        StringBuilder stringBuilder = new StringBuilder();

        if (models.length == 0)
            return stringBuilder;

        Map<String, String> map = models[0].getMap();
        stringBuilder.append("<table  class=\"w-auto table models-table table-bordered table-hover table-sm\">\n<thead>\n<tr>\n");

        for (String key : map.keySet()) {
            stringBuilder.append("<th scope=\"col\">").append(key).append("</th>\n");
        }

        stringBuilder.append("</tr>\n</thead>\n<tbody>\n");
        for (IDefaultModel model : models) {
            stringBuilder.append(getRow(model));
        }
        stringBuilder.append("</tbody>\n</table>\n");

        return stringBuilder;
    }

    public static StringBuilder getRow(IDefaultModel model) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> map = model.getMap();

        stringBuilder.append("<tr onclick=\"window.location.href = '")
                .append(model.getURLPattern()).append("?id=")
                .append(model.getId())
                .append("';\">\n");
        for (String value : map.values()) {
            stringBuilder.append("<td>").append(value).append("</td>\n");
        }
        stringBuilder.append("</tr>\n");

        return stringBuilder;
    }
}
