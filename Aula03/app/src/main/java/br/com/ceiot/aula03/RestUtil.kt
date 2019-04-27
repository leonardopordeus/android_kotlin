package br.com.ceiot.aula03

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * TODO (9) Criando classe para implementação de Serviços Rest.
 */
class RestUtil {

    internal val BASE_URL = "https://ceiot-android-app.firebaseio.com/"
    internal val PARAM_ORDER_BY = "orderBy"
    internal val NOME = "nome"
    internal val EQUAL_TO = "equalTo"

    fun nodeService() : NodeService
    {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(NodeService::class.java)
        return service
    }


//    /**
//     * Constroi a URL a ser utilizada.
//     *
//     * @param query valor utilziado para consulta
//     * @return URL montada
//     */
//    public static URL buildUrl(String entity, String query) {
//        Uri builtUri = Uri.parse(BASE_URL+ "/"  + entity +".json" ).buildUpon()
////                .appendQueryParameter(PARAM_ORDER_BY, NOME)
////                .appendQueryParameter(EQUAL_TO, query)
//            .build();
//
//        URL url = null;
//        try {
//            url = new URL(builtUri.toString());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        return url;
//    }

//    /**
//     * Retorna um JSON a partir de uma requisição HTTP
//     * @param url
//     * @return String JSON
//     * @throws IOException
//     */
//    public static String doGet(URL url) throws IOException
//    {
//        StringBuilder sb = new StringBuilder("");
//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        try {
//            urlConnection.setRequestMethod("GET");
//
//            urlConnection.connect();
//            int responseCode = urlConnection.getResponseCode();
//
//            switch (responseCode) {
//                case HttpURLConnection.HTTP_OK:
//                case HttpURLConnection.HTTP_CREATED:
//                InputStream inputStream = urlConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    sb.append(line+"\n");
//                }
//                bufferedReader.close();
//                break;
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        finally {
//            urlConnection.disconnect();
//        }
//        return sb.toString();
//    }
//
//    public static String doPost(URL url, String json) throws IOException
//    {
//        StringBuilder sb = new StringBuilder("");
//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        try {
//            urlConnection.setRequestMethod("POST");
//            urlConnection.setDoInput(true);
//            urlConnection.setDoOutput(true);
//
//            //urlConnection.getOutputStream().write(json.getBytes());
//
//            OutputStream outputStream = urlConnection.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(
//                new OutputStreamWriter(outputStream, "UTF-8"));
//            writer.write(json);
//            writer.flush();
//            writer.close();
//            outputStream.close();
//
//            int responseCode=urlConnection.getResponseCode();
//
//            switch (responseCode) {
//                case HttpURLConnection.HTTP_OK:
//                case HttpURLConnection.HTTP_CREATED:
//                InputStream inputStream = urlConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    sb.append(line+"\n");
//                }
//                bufferedReader.close();
//                break;
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        finally {
//            urlConnection.disconnect();
//        }
//        return sb.toString();
//    }
//
//    public static List<Node> parseJSONArray(String json) {
//        List<Node> listNode = new ArrayList<>();
//        try {
//            JSONObject jsonObject = new JSONObject(json);
//            //JSONArray jsonArray = jsonObject.getJSONArray("nodes");
//            JSONArray jsonArray = jsonObject.names();
//            for (int i = 0; i <jsonArray.length(); i++) {
//                //JSONObject obj = jsonArray.getJSONObject(i);
//                JSONObject obj = jsonObject.getJSONObject(jsonArray.getString(i));
//                String nome = obj.getString("nome");
//                String descricao = obj.getString("descricao");
//                String localizacao = obj.getString("localizacao");
//                Node node = new Node(nome,descricao,localizacao);
//                listNode.add(node);
//            }
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return listNode;
//    }
//
//    public static String saveOnFirebase(URL url,Node node)
//    {
//        JSONObject jsonObject= new JSONObject();
//        try {
//            jsonObject.put("nome", node.getNome());
//            jsonObject.put("descricao", node.getDescricao());
//            jsonObject.put("localizacao", node.getLocalizacao());
//
//            return doPost(url,jsonObject.toString());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//
//    }
}