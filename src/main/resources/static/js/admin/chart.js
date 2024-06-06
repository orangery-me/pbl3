let currentDate = new Date();
let currentYear = currentDate.getFullYear();
let dates = [];
let datelyRevenue = [];
let monthlyRevenue = [];

function getRevenueOfDay (date) {
    return fetch("/api/admin/getRevenueOfDay?date=" + date)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            return data;
        })
        .catch(error => console.error(error));
}
function getRevenueOfMonth (month, year) {
    return fetch("/api/admin/getRevenueOfMonth?month=" + month + "&year=" + year)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            return data;
        })
        .catch(error => console.error(error));
}

for (let i = 0; i < 7; i++) {
    let date = new Date(currentDate.getTime() - (i * 24 * 60 * 60 * 1000)); // subtract i days in milliseconds
    let dateString = date.getFullYear() + '-' + (date.getMonth() + 1).toString().padStart(2, '0') + '-' + date.getDate().toString().padStart(2, '0');
    dates.push(dateString);
    datelyRevenue.push(getRevenueOfDay(dateString));
}

for (let i = 0; i < 12; i++) {
    monthlyRevenue.push(getRevenueOfMonth(i + 1, currentYear));
}


Promise.all([Promise.all(datelyRevenue), Promise.all(monthlyRevenue)]).then(([datelyRevenue, monthlyRevenue]) => {
    let ctx = document.getElementById("myChart-weekly").getContext("2d");
    let myChart = new Chart(ctx, {
        type: "line",
        data: {
            labels: [
                dates[0],
                dates[1],
                dates[2],
                dates[3],
                dates[4],
                dates[5],
                dates[6],
            ],
            datasets: [
                {
                    label: "đồng",
                    data: [
                        datelyRevenue[0],
                        datelyRevenue[1],
                        datelyRevenue[2],
                        datelyRevenue[3],
                        datelyRevenue[4],
                        datelyRevenue[5],
                        datelyRevenue[6],
                    ],
                    backgroundColor: "rgba(48, 116, 3, 0.8)",
                },
            ],
        },
    });
    let ctx2 = document.getElementById("myChart-monthly").getContext("2d");
    let myChart2 = new Chart(ctx2, {
        type: "line",
        data: {
            labels: ["T1" + currentYear, "T2" + currentYear, "T3" + currentYear, "T4" + currentYear, "T5" + currentYear, "T6" + currentYear, "T7" + currentYear, "T8" + currentYear, "T9" + currentYear, "T10" + currentYear, "T11" + currentYear, "T12" + currentYear],
            datasets: [
                {
                    label: "đồng",
                    data: [
                        monthlyRevenue[0],
                        monthlyRevenue[1],
                        monthlyRevenue[2],
                        monthlyRevenue[3],
                        monthlyRevenue[4],
                        monthlyRevenue[5],
                        monthlyRevenue[6],
                        monthlyRevenue[7],
                        monthlyRevenue[8],
                        monthlyRevenue[9],
                        monthlyRevenue[10],
                        monthlyRevenue[11],
                    ],
                    backgroundColor: "rgba(48, 50, 3, 0.8)",
                },
            ],
        },
    });
}).catch((error) => console.error(error));
