package com.flowring.cn.util;

import java.util.Map;
import java.util.Set;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.Scriptable;

public class ScriptUtils {

	public static boolean eval(String script, Map<String, String> data) {

		boolean result = false;

		try {
			Context cx = Context.enter();

			Scriptable scope = cx.initStandardObjects();
			Object r = null;

			for (Map.Entry<String, String> entry : data.entrySet()) {
				System.out.println("Item : " + entry.getKey() + " Value : " + entry.getValue());
				scope.put(entry.getKey(), scope, entry.getValue());
			}

			r = cx.evaluateString(scope, script, "ruleScript", 1, null);

			System.out.println("evaluate script:" + script);

			result = Boolean.parseBoolean(r.toString());
		} catch (JavaScriptException jse) {
			jse.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Context.exit();
		}

		return result;
	}

}
