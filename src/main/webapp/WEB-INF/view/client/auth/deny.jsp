<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>404 Not Found</title>
                <!-- Bootstrap CSS -->
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                <style>
                    body {
                        background-color: #f8f9fa;
                        height: 100vh;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        margin: 0;
                    }

                    .error-container {
                        text-align: center;
                    }

                    .error-code {
                        font-size: 8rem;
                        font-weight: bold;
                        color: #6c757d;
                    }

                    .error-message {
                        font-size: 1.5rem;
                        margin-bottom: 1rem;
                    }

                    .btn-home {
                        font-size: 1.2rem;
                    }
                </style>
            </head>

            <body>
                <div class="container error-container">
                    <div class="row">
                        <div class="col-12">
                            <h1 class="error-code">404</h1>
                            <p class="error-message">Oops! The page you are looking for does not exist.</p>
                            <a href="/" class="btn btn-primary btn-home">Go Back Home</a>
                        </div>
                    </div>
                </div>

                <!-- Bootstrap JS -->
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
            </body>

            </html>