define(["ag-grid", "lodash", "jquery"], (ag, _, $) => {
    let size = 20;
    let pageCurrent = 0;
    let pageLast;
    let totalRows;

    const pagination = {
        item: {
            from: $(".pagination.from-item"),
            to: $(".pagination.to-item"),
            of: $(".pagination.of-item")
        },
        page: {
            current: $(".pagination.current-page"),
            of: $(".pagination.from-page")
        },
        handler: {
            first: $(".ag-paging-button.pagination-first"),
            last: $(".ag-paging-button.pagination-last"),
            previous: $(".ag-paging-button.pagination-previous"),
            next: $(".ag-paging-button.pagination-next")
        }
    };

    const columnDefs = [
        {headerName: "Full Name", field: "fullName"},
        {headerName: "Email", field: "email"},
        {headerName: "Date", field: "date"},
        {headerName: "Phone Number", field: "phoneNumber"},
        {headerName: "Comment", field: "comment"},
        {headerName: "Medic", field: "medic"},
        {headerName: "Procedure", field: "procedure"}
    ];

    const grid = document.querySelector('#appointment-grid');
    const gridOptions = {
        columnDefs: columnDefs,
        datasource: {
            pageSize: size,
        },
        enableSorting: true,
        enableFilter: true,
        suppressSizeToFit: true,
        enableColResize: true,
        onGridReady: (params) => {
            loadAppointments(pageCurrent, size);
            params.api.sizeColumnsToFit();
        }
    };

    new ag.Grid(grid, gridOptions);

    pagination.handler.first.on("click", () => {
        pageCurrent = 0;
        loadAppointments(pageCurrent, size);
    });

    pagination.handler.previous.on("click", () => {
        if (pageCurrent > 0) {
            loadAppointments(--pageCurrent, size);
        }
    });

    pagination.handler.last.on("click", () => {
        pageCurrent = pageLast - 1;
        loadAppointments(pageCurrent, size);
    });

    pagination.handler.next.on("click", () => {
        if (pageCurrent + 1 < pageLast) {
            loadAppointments(++pageCurrent, size);
        }
    });

    const loadAppointments = (page, size) => {
        $.get("/appointment?page=" + page + "&size=" + size, (data) => {
            const appointments = _.get(data, "_embedded.appointment", []);
            const page = data.page;

            _.map(appointments, (appointment) => {
                appointment.medic =
                    _.get(appointment, "medic.firstName", "---") + " " +
                    _.get(appointment, "medic.lastName", "---");
                appointment.procedure = appointment.procedure.name;
                return appointment;
            });

            updatePaginationView(page);
            gridOptions.api.setRowData(appointments);
        });
    };

    const updatePaginationView = (page) => {
        pageLast = page.totalPages;
        totalRows = _.get(page, "totalElements", 0);

        pagination.item.from.html((pageCurrent) * size + 1);
        pagination.item.to.html((pageCurrent) * size + size);
        pagination.item.of.html(totalRows);

        pagination.page.current.html(pageCurrent + 1);
        pagination.page.of.html(pageLast);
    };
});