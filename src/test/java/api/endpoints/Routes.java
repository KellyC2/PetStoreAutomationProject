package api.endpoints;

public class Routes {
    public static String baseUrl = "https://petstore.swagger.io/v2";
    public static String createUser;
    public static String retrieveUser;
    public static String updateUser;
    public static String deleteUser;
    public static String addNewPet;
    public static String retrievePet;
    public static String updatePet;
    public static String deletePet;
    public static String placeOrder;
    public static String retrievePurchaseOrder;
    public static String deletePurchaseOrder;

    static  {
        createUser = baseUrl + "/user";
        retrieveUser = baseUrl + "/user/{username}";
        updateUser = baseUrl + "/user/{username}";
        deleteUser = baseUrl + "/user/{username}";

        addNewPet = baseUrl + "/pet";
        retrievePet = baseUrl + "/pet/{petId}";
        updatePet = baseUrl + "/pet/{petId}";
        deletePet = baseUrl + "/pet/{petId}";

        placeOrder = baseUrl + "/store/order";
        retrievePurchaseOrder = baseUrl + "/store/order/{orderId}";
        deletePurchaseOrder = baseUrl + "/store/order/{orderId}";
    }
}
