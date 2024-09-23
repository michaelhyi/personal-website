const MONTHS = [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December",
];

export default function format(date) {
    const obj = new Date(date);
    const month = MONTHS[obj.getMonth()];
    const day = obj.getDate();
    const year = obj.getFullYear();

    return `${month} ${day}, ${year}`;
}
