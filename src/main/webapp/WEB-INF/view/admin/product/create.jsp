<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="" />
                <meta name="author" content="" />
                <title>Create Product - SB Admin</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

                <script>
                    $(document).ready(() => {
                        const avatarFile = $("#avatarFile");
                        avatarFile.change(function (e) {
                            const imgURL = URL.createObjectURL(e.target.files[0]);
                            $("#avatarPreview").attr("src", imgURL);
                            $("#avatarPreview").css({ "display": "block" });
                        });
                    });
                </script>

            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Manager Product</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item active"><a href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item active">Product</li>
                                </ol>
                                <!-- <div></div> -->
                            </div>
                            <div class=" mt-5">
                                <div class="row">
                                    <div class="col-md-6 col-12 mx-auto">
                                        <h3>Create Product</h3>
                                        <hr />
                                        <form:form method="post" action="/admin/product/create"
                                            modelAttribute="newProduct" enctype="multipart/form-data">
                                            <div class="row g-3">
                                                <div class="col mb-3">
                                                    <c:set var="nameHasBindError">
                                                        <form:errors path="name" />
                                                    </c:set>
                                                    <label class="from-label">Name:</label>
                                                    <form:input type="text"
                                                        class="form-control ${not empty nameHasBindError ? 'is-invalid' : ''}"
                                                        placeholder="" path="name" />
                                                    <form:errors path="name" cssClass="invalid-feedback" />
                                                </div>
                                                <div class="col mb-3">
                                                    <c:set var="priceHasBindError">
                                                        <form:errors path="price" />
                                                    </c:set>
                                                    <label class="from-label">Price:</label>
                                                    <form:input type="number"
                                                        class="form-control ${not empty priceHasBindError ? 'is-invalid' : ''}"
                                                        placeholder="" path="price" />
                                                    <form:errors path="price" cssClass="invalid-feedback" />
                                                </div>
                                            </div>
                                            <div class="col mb-3">
                                                <c:set var="detailDescHasBindError">
                                                    <form:errors path="detailDesc" />
                                                </c:set>
                                                <label class="from-label">Detail description:</label>
                                                <form:textarea type="text"
                                                    class="form-control ${not empty detailDescHasBindError ? 'is-invalid' : ''}"
                                                    placeholder="" path="detailDesc" />
                                                <form:errors path="detailDesc" cssClass="invalid-feedback" />
                                            </div>
                                            <div class="row g-3">
                                                <div class="col mb-3">
                                                    <c:set var="shortDescHasBindError">
                                                        <form:errors path="shortDesc" />
                                                    </c:set>
                                                    <label class="from-label">Short desciption:</label>
                                                    <form:input type="text"
                                                        class="form-control ${not empty shortDescHasBindError ? 'is-invalid' : ''}"
                                                        placeholder="" path="shortDesc" />
                                                    <form:errors path="shortDesc" cssClass="invalid-feedback" />
                                                </div>
                                                <div class="col mb-3">
                                                    <c:set var="quanityHasBindError">
                                                        <form:errors path="shortDesc" />
                                                    </c:set>
                                                    <label class="from-label">Quantity:</label>
                                                    <form:input type="text"
                                                        class="form-control ${not empty quanityHasBindError ? 'is-invalid' : ''}"
                                                        placeholder="" path="quantity" />
                                                    <form:errors path="quantity" cssClass="invalid-feedback" />
                                                </div>
                                            </div>

                                            <div class="row g-3">
                                                <div class="col">
                                                    <label for="formFile" class="form-label">Facroty:</label>
                                                    <form:select class="form-select" aria-label="Role" path="factory">
                                                        <form:option value="MacBook">MacBook</form:option>
                                                        <form:option value="MSI">MSI</form:option>
                                                        <form:option value="HP">HP</form:option>
                                                        <form:option value="Acer">Acer</form:option>
                                                        <form:option value="SamSung">SamSung</form:option>
                                                    </form:select>
                                                </div>
                                                <div class="col">
                                                    <label for="formFile" class="form-label">Target:</label>
                                                    <form:select class="form-select" aria-label="Role" path="target">
                                                        <form:option value="Gaming">Gaming</form:option>
                                                        <form:option value="Office">Văn phòng</form:option>
                                                        <form:option value="Diagram">Đồ hoạ</form:option>
                                                    </form:select>
                                                </div>

                                            </div>
                                            <div class="row g-3">
                                                <div class="col mb-3">
                                                    <label for="formFile" class="form-label">Avatar:</label>
                                                    <input class="form-control" type="file" id="avatarFile"
                                                        accept=".png, .jpg, .jpeg" name="productFile" />
                                                </div>
                                            </div>
                                            <div class="col-12 mb-3">
                                                <img style="max-height: 250px; display: none;" alt="avatar preview"
                                                    id="avatarPreview" />
                                            </div>
                                            <div class="col-12 mb-5">
                                                <button type="submit" class="btn btn-primary">Submit</button>
                                            </div>
                                        </form:form>
                                    </div>
                                </div>
                            </div>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/scripts.js"></script>
            </body>

            </html>