<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Danh sách mặt bằng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <script>
        function confirmDelete(ma) {
            if (confirm('Bạn có chắc chắn muốn xóa mặt bằng với mã số ' + ma + ' không?')) {
                const f = document.getElementById('delForm');
                f.ma.value = ma;
                f.submit();
            }
        }
    </script>
</head>
<body class="bg-light">
<div class="container mt-5 card p-4 shadow-sm">
    <h2 class="mb-4 text-center">Danh sách mặt bằng</h2>

    <!-- Form tìm kiếm -->
    <form action="${pageContext.request.contextPath}/matbang/search" method="get" class="row g-3 mb-4 align-items-end">

        <!-- Loại mặt bằng -->
        <div class="col-md-3">
            <label class="form-label">Loại mặt bằng</label>
            <select name="loai" class="form-select">
                <option value="">-- Tất cả --</option>
                <option value="Văn phòng chia sẻ" ${loai == 'Văn phòng chia sẻ' ? 'selected' : ''}>Văn phòng chia sẻ
                </option>
                <option value="Văn phòng trọn gói" ${loai == 'Văn phòng trọn gói' ? 'selected' : ''}>Văn phòng trọn
                    gói
                </option>
            </select>
        </div>

        <!-- Tầng -->
        <div class="col-md-2">
            <label class="form-label">Tầng</label>
            <select name="tang" class="form-select">
                <option value="">-- Tất cả --</option>
                <c:forEach var="i" begin="1" end="15">
                    <option value="${i}" ${tang != null && tang == i ? 'selected' : ''}>
                            ${i}
                    </option>
                </c:forEach>
            </select>
        </div>

        <!-- xuống dòng -->
        <div class="w-100"></div>

        <!-- Gia dau & Gia ket thuc -->
        <div class="col-md-2">
            <label class="form-label">Giá từ</label>
            <input type="number" name="giaMin" class="form-control"
                   value="${giaMin != null ? giaMin : ''}" min="0" step="1000"/>
        </div>

        <div class="col-md-2">
            <label class="form-label">đến</label>
            <input type="number" name="giaMax" class="form-control"
                   value="${giaMax != null ? giaMax : ''}" min="0" step="1000"/>
        </div>

        <!-- xuống dòng -->
        <div class="w-100"></div>

            <!-- Ngày cho thuê & Ngày kết thúc -->
            <div class="col-md-6">
                <label class="form-label">Ngày cho thuê</label>
                <input type="date" name="from" placeholder="dd/MM/yyyy" class="form-control"/>
            </div>
            <div class="col-md-6">
                <label class="form-label">Ngày kết thúc</label>
                <input type="date" name="to" placeholder="dd/MM/yyyy" class="form-control"/>
            </div>

        <!-- Nút bấm -->
        <div class="col-12">
            <button type="submit" class="btn btn-primary">Tìm kiếm</button>
            <a href="${pageContext.request.contextPath}/matbang/add" class="btn btn-success">+ Thêm mới</a>
        </div>
    </form>

    <!-- Bảng danh sách -->
    <div class="table-responsive">
        <table class="table table-bordered table-hover align-middle">
            <thead class="table-light">
            <tr>
                <th>Mã MB</th>
                <th>Diện tích (m²)</th>
                <th>Trạng thái</th>
                <th>Tầng</th>
                <th>Loại</th>
                <th>Giá cho thuê (VNĐ)</th>
                <th>Ngày bắt đầu</th>
                <th>Ngày kết thúc</th>
                <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list}" var="m">
                <tr>
                    <td>${m.maMb}</td>
                    <td>${m.dienTich}</td>
                    <td>${m.trangThai}</td>
                    <td>${m.tang}</td>
                    <td>${m.loai}</td>
                    <td><fmt:formatNumber value="${m.giaTien}" type="currency" currencySymbol=""/></td>
                    <td>${m.ngayBatDau}</td>
                    <td>${m.ngayKetThuc}</td>
                    <td>
                        <button type="button" class="btn btn-sm btn-danger"
                                onclick="confirmDelete('${m.maMb}')">Xóa
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Form delete hidden -->
    <form id="delForm" action="${pageContext.request.contextPath}/matbang/delete" method="post" style="display:none;">
        <input type="hidden" name="ma"/>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
