import org.junit.Test;

import java.util.LinkedHashMap;

/**
 * @author liuhx on 2017/5/22 11:14
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class SqlTest {

    @Test
    public void update1() {
        try {
//            String sql = "update customer set status = :status,haixiao = :123haixiao123,ty=:ty ,name = :name,sex=:sex,remark=:remark,a=:a,b=:b,c=:d where id=:id and df=:df and cbd=:cbddd";
            /*String sql = "update t_pinjiantuan\n" +
                    "        set\n" +
                    "        status = :status,houseid = :houseid,copy_writer = :copy_writer,content = :content,content2 = :content2,customerid =\n" +
                    "        :customerid,creator = :creator,created = :created,updator = :updator,updated = :updated\n" +
                    "        where\n" +
                    "        id = :id";*/

            String sql = "update t_pinjiantuan\n" +
                    "        set\n" +
                    "        houseid = :houseid,\n" +
                    "        copy_writer = :copy_writer,\n" +
                    "        content = :content,\n" +
                    "        content2 = :content2,\n" +
                    "        customerid =\n" +
                    "        :customerid,\n" +
                    "        status = :status,\n" +
                    "        creator = :creator,\n" +
                    "        created = :created,\n" +
                    "        updator = :updator,\n" +
                    "        updated = :updated\n" +
                    "        where\n" +
                    "        id = :id\n";

            LinkedHashMap<String, Object> params = new LinkedHashMap();
//            params.put("status", 1);
            params.put("sex", null);
            params.put("name", 1);
            params.put("copy_writer", "haha");
            params.put("c", "haha");
            params.put("45", "haha");
            params.put("4545", "haha");
            params.put("id", 2);
            params.put("df", null);
//            sql = sql.replaceAll("\n", " ");
            String startSQL = sql.substring(0, sql.indexOf("set")).concat("set ");
            String setSQL = sql.substring(sql.lastIndexOf("set")+3, sql.indexOf("where"));
            String whereSQL = sql.substring(sql.lastIndexOf("where")+5, sql.length());

            String[] sqlColumns = setSQL.split(",");
            for (String column : sqlColumns) {
                String field = column.substring(column.lastIndexOf(":")+1, column.length());
                if (!params.containsKey(field.trim())) {
                    setSQL = setSQL.replaceFirst(":" + field.trim() + "\\b", " haixiaonull ");
                }
            }
            String replaceSetSQL = setSQL.replaceAll("(,\\s*?\\w*\\s*=\\s*haixiaonull\\b\\s*)", "  ");
            replaceSetSQL = replaceSetSQL.replaceAll("(,?\\w*\\s*=\\s*haixiaonull\\b\\s*)", "  ");

            String resultSQL = startSQL.concat(replaceSetSQL).concat(" where ").concat(whereSQL);
            resultSQL = resultSQL.replaceAll("(and\\s*$|or\\s*$)", " ");
            resultSQL = resultSQL.replaceAll(",\\s*where\\s*", " where ");
            resultSQL = resultSQL.replaceAll("set\\s*,\\s*", "set ");
//            System.out.println(resultSQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void update2() {
        String sql = "update t_pinjiantuan\n" +
                "        set\n" +
                "        houseid = :houseid,\n" +
                "        copy_writer = :copy_writer,\n" +
                "        content = :content,\n" +
                "        content2 = :content2,\n" +
                "        customerid =\n" +
                "        :customerid,\n" +
                "        status = :status,\n" +
                "        creator = :creator,\n" +
                "        created = :created,\n" +
                "        updator = :updator,\n" +
                "        updated = :updated\n" +
                "        where\n" +
                "        id = :id\n";

    }
}
