(function () {
    const $ = (s) => document.querySelector(s);
    const codeRe = /^[A-Z0-9]{3}-[A-Z0-9]{2}-[A-Z0-9]{2}$/;
    const dateRe = /^\d{2}\/\d{2}\/\d{4}$/; // dd/MM/yyyy


    function parseDate(d) {
        const [dd, mm, yyyy] = d.split('/').map(Number);
        return new Date(yyyy, mm - 1, dd);
    }


    const form = document.getElementById('addForm');
    if (!form) return;


    form.addEventListener('submit', function (e) {
        let ok = true;
        const errs = [];
        const ma = $('#ma').value.trim();
        const dienTich = parseInt($('#dienTich').value, 10);
        const tang = parseInt($('#tang').value, 10);
        const gia = parseInt($('#giaTien').value, 10);
        const s = $('#ngayBatDau').value.trim();
        const eDate = $('#ngayKetThuc').value.trim();


        if (!codeRe.test(ma)) {
            ok = false;
            errs.push('Mã mặt bằng không đúng định dạng XXX-XX-XX');
        }
        if (!(dienTich > 20)) {
            ok = false;
            errs.push('Diện tích phải > 20');
        }
        if (!(tang >= 1 && tang <= 15)) {
            ok = false;
            errs.push('Tầng 1-15');
        }
        if (!(gia > 1000000)) {
            ok = false;
            errs.push('Giá tiền > 1,000,000');
        }
        if (!dateRe.test(s) || !dateRe.test(eDate)) {
            ok = false;
            errs.push('Ngày dd/MM/yyyy');
        } else {
            const d1 = parseDate(s), d2 = parseDate(eDate);
            const min = new Date(d1);
            min.setMonth(min.getMonth() + 6);
            if (!(d2 >= min)) {
                ok = false;
                errs.push('Kết thúc >= bắt đầu + 6 tháng');
            }
        }


        if (!ok) {
            e.preventDefault();
            alert(errs.join('\n'));
        }
    });
})();