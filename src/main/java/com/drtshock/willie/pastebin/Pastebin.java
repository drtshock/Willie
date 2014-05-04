package com.drtshock.willie.pastebin;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Pastebin {

	private static String token;
	private static String devkey;

	private static String checkResponse(String response) {
		if (response.substring(0, 15).equals("Bad API request")) {
			return response.substring(17);
		}

		return "";
	}

	/**
	 * Logs in to Pastebin. This is automatically run once on startup and
	 * should not be run again unless a future paste should be pasted for a
	 * different user.
	 *
	 * @param username username
	 * @param password password
	 * @return result of login
	 */
	public static String login(String username, String password) {
		final String api_user_name;
		final String api_user_password;

		try {
			api_user_name = URLEncoder.encode(username, "UTF-8");
			api_user_password = URLEncoder.encode(password, "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
			return null;
		}

		final String data = "api_dev_key=" + devkey + "&api_user_name=" + api_user_name + "&api_user_password=" + api_user_password;
		final String loginURL = "http://www.pastebin.com/api/api_login.php";
		final String response = page(loginURL, data);
		final String check = checkResponse(response);

		if (!check.isEmpty()) {
			return "false";
		}

		token = response;
		return response;
	}

	/**
	 * Creates a paste.
	 *
	 * @param code code to paste
	 * @param name title of the paste
	 * @param format language of the code (available languages:
	 * pastebin.com/api#5")
	 * @return URL of paste or error message if there is one
	 * @throws UnsupportedEncodingException
	 */
	public static String makePaste(String code, String name, String format) throws UnsupportedEncodingException {
		final String content = URLEncoder.encode(code, "UTF-8");
		final String title = URLEncoder.encode(name, "UTF-8");
		final String data = "api_option=paste&api_user_key=" + token + "&api_paste_private=0&api_paste_name=" + title + "&api_paste_expire_date=N&api_paste_format=" + format + "&api_dev_key=" + devkey + "&api_paste_code=" + content;
		final String pasteURL = "http://www.pastebin.com/api/api_post.php";
		final String response = page(pasteURL, data);
		final String check = checkResponse(response);

		if (!check.isEmpty()) {
			return "";
		}

		return response;
	}

	private static String page(String uri, String urlParameters) {
		URL url;
		HttpURLConnection connection = null;

		try {
			url = new URL(uri);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			final DataOutputStream wr = new DataOutputStream(connection.getOutputStream());

			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			final InputStream is = connection.getInputStream();
			final BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			final StringBuilder response = new StringBuilder();

			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line).append("\r\n");
			}

			rd.close();
			return response.toString();

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	/**
	 * Sets the API key to use. This is run automatically on startup and does
	 * not need to be run again unless needed.
	 *
	 * @param devkey API key to use
	 */
	public static void setDevkey(String devkey) {
		Pastebin.devkey = devkey;
	}

}
