package com.ruoyi.system.datascope;

import java.util.HashMap;
import java.util.Map;

public class BaseEntity {
     Map<String , Object>  params;

    public Map<String, Object> getParams() {
        if (params == null)
        {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params) {

        this.params = params;
    }
}
