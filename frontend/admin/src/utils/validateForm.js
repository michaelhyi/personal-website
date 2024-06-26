export default function validateForm(text, image, showImage) {
    if (!text || text.length === 0) {
        throw new Error("Fields cannot be blank.");
    }

    const titleIndex = text.search("</h1>");
    const title = text.substring(4, titleIndex);
    const content = text.substring(titleIndex + 5);

    if (!title || title.length === 0)
        throw new Error("Title cannot be blank.");

    if (!content || content.length === 0)
        throw new Error("Content cannot be blank.");

    if (!image && !showImage) throw new Error("An image is required.");
}
