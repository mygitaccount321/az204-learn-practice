function sample(prefix) {
        var context = getContext();
        var response = context.getResponse();

        response.setBody("Hello, World "+prefix);

}
