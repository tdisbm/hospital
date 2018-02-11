"use strict";

define([
    "moment",
    "field.util",
    "medic.util",
    "lodash",
    "jquery",
], (moment, fieldUtil, medicUtil, _, $) => {
    $(document).ready(() => {
        const form = $("#booking-form");
        const medicSelect = form.find(".medic-select");
        const availabilityInput = form.find(".availability-input");
        const messageBox = $("#message");

        /**
         * Initialization
         */
        medicUtil
            .setResponseHandler(200, () => {
                messageBox.attr("class", "alert alert-success");
                messageBox.html("Appointment available");
            })
            .setResponseHandler(208, () => {
                messageBox.attr("class", "alert alert-warning");
                messageBox.html("Appointment not available");
            });

        fieldUtil
            .ajaxFill({
                selector: ".medic-select",
                ajax_url: "/medic",
                factory: (domElement, data) => {
                    const medics = _.get(data, "_embedded.medic", []);
                    _.forEach(medics, (medic) => {
                        domElement.append($("<option></option>")
                            .val(medic.id)
                            .text(_.get(medic, "firstName") + " " + _.get(medic, "lastName"))
                        );
                    });
                }
            })
            .ajaxFill({
                selector: ".procedure-select",
                ajax_url: "/procedure",
                factory: (domElement, data) => {
                    const procedures = _.get(data, "_embedded.procedure", []);
                    _.forEach(procedures, (procedure) => {
                        domElement.append($("<option></option>")
                            .val(procedure.id)
                            .text(procedure.name)
                        );
                    });
                }
            });

        availabilityInput.datetimepicker({
            format: "MM/DD/YYYY HH:mm",
            minDate: new Date()
        }).on("dp.change", () => {
            medicUtil.checkAvailability({
                medicId: medicSelect.val(),
                date: availabilityInput.val()
            })
        });

        medicSelect.on("change", () => {
            medicUtil.checkAvailability({
                medicId: medicSelect.val(),
                date: availabilityInput.val()
            });
        });

        $(document).on("submit", form, (event) => {
            event.preventDefault();
            let requestBody = {};
            let path;
            let ref;
            const fields = form.find("[data-value-path]");

            /**
             * mapping form data to request body by dom attribute
             */
            _.forEach(fields, (field) => {
                field = $(field);
                path = field.attr("data-value-path");
                ref = requestBody;
                _.forEach(path.split("."), (piece) => {
                    if (!ref.hasOwnProperty(piece)) {
                        ref[piece] = {};
                    }
                    ref = ref[piece];
                });

                _.set(requestBody, path, field.val());
            });

            /**
             * form validation
             * @type {boolean}
             */
            let invalidForm = false;
            _.forEach(requestBody, (value) => {
                if (value === "") {
                    invalidForm = true;
                }
            });
            if (invalidForm) {
                messageBox.attr("class", "alert alert-danger");
                messageBox.html("Please complete all fields");
                return;
            }

            /**
             * Form submission
             */
            $.ajax({
                url : form.attr("action"),
                type : form.attr("method"),
                data : JSON.stringify(requestBody),
                processData: false,
                contentType: "application/json",
            }).always((body, status_text, response) => {
                const response_status = response.status || body.status;
                switch (response_status) {
                    case 201:
                        _.forEach(fields, (field) => {
                            $(field).val("")
                        });
                        messageBox.attr("class", "alert alert-success");
                        messageBox.html("Appointment created");
                        break;
                    case 208:
                        messageBox.attr("class", "alert alert-info");
                        messageBox.html("This time is already booked or it's unavailable");
                        break;
                    case 400:
                    case 500:
                        messageBox.attr("class", "alert alert-danger");
                        messageBox.html("Something goes wrong");
                        break;
                }
            });
        });
    });
});
