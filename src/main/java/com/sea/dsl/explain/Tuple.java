package com.sea.dsl.explain;

import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;

public class Tuple {
    private Row row;
    private StructField[] fields;

    public Tuple(Row row) {
        this.row = row;
        fields = row.schema().fields();
    }

    public Object getAt(String field) throws Exception {
        int i;
        StructField structField = null;
        for (i = 0; i < fields.length; i++) {
            if (fields[i].name().equals(field)) {
                structField = fields[i];
                break;
            }
        }

        if (structField == null)
            throw new Exception("no such filed " + field);

        if (structField.dataType() == DataTypes.LongType)
            return row.getLong(i);
        else if (structField.dataType() == DataTypes.StringType)
            return row.getString(i);
        else if (structField.dataType() == DataTypes.DoubleType)
            return row.getDouble(i);
        else if (structField.dataType() == DataTypes.TimestampType)
            return row.getTimestamp(i);
        else if (structField.dataType() == DataTypes.BooleanType)
            return row.getBoolean(i);
        else if (structField.dataType() == DataTypes.ByteType)
            return row.getByte(i);
        else
            return null;
    }
}
