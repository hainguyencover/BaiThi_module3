<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Thêm mặt bằng</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/assets/js/validate.js"></script>
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="mb-4 text-center">Thêm mặt bằng</h2>

    <c:if test="${not empty errors}">
        <div class="alert alert-danger">
            <c:forEach var="e" items="${errors}">
                <div>${e.value}</div>
            </c:forEach>
        </div>
    </c:if>

    <form id="addForm" action="${pageContext.request.contextPath}/matbang/add" method="post" class="card p-4 shadow-sm">
        <div class="mb-3">
            <label class="form-label">Mã mặt bằng (*)</label>
            <input id="ma" name="ma" class="form-control" required
                   pattern="^[A-Z0-9]{3}-[A-Z0-9]{2}-[A-Z0-9]{2}$" placeholder="XXX-XX-XX"/>
        </div>

        <div class="mb-3">
            <label class="form-label">Diện tích (*)</label>
            <input id="dienTich" name="dienTich" type="number" class="form-control" min="21" required/>
        </div>

        <div class="mb-3">
            <label class="form-label">Trạng thái</label>
            <select name="trangThai" class="form-select">
                <option>Trống</option>
                <option>Hạ tầng</option>
                <option>Đầy đủ</option>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Tầng (*)</label>
            <select id="tang" name="tang" class="form-select" required>
                <c:forEach var="i" begin="1" end="15">
                    <option value="${i}">${i}</option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Loại mặt bằng</label>
            <select name="loai" class="form-select">
                <option>Văn phòng chia sẻ</option>
                <option>Văn phòng trọn gói</option>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Mô tả chi tiết</label>
            <textarea name="moTa" rows="3" class="form-control"></textarea>
        </div>

        <div class="mb-3">
            <label class="form-label">Giá cho thuê (*)</label>
            <div class="input-group">
                <label for="giaTien"></label>
                <input id="giaTien" name="giaTien" type="number" min="1000000" step="1000" required
                       class="form-control"/>
                <span class="input-group-text">VND</span>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">Ngày bắt đầu (*)</label>
                <input type="date" id="ngayBatDau" name="ngayBatDau" class="form-control" placeholder="dd/MM/yyyy"
                       required pattern="^\d{2}/\d{2}/\d{4}$"/>
            </div>
            <div class="col-md-6 mb-3">
                <label class="form-label">Ngày kết thúc (*)</label>
                <input type="date" id="ngayKetThuc" name="ngayKetThuc" class="form-control" placeholder="dd/MM/yyyy"
                       required pattern="^\d{2}/\d{2}/\d{4}$"/>
            </div>
        </div>

        <div class="d-flex justify-content-center gap-3 mt-4">
            <button type="submit" class="btn btn-primary">Lưu</button>
            <a href="${pageContext.request.contextPath}/matbang" class="btn btn-secondary">Hủy</a>
        </div>
    </form>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
