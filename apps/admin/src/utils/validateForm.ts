/* eslint-disable @typescript-eslint/no-non-null-assertion -- text is never undefined */

export function validateForm(
  text: string | undefined,
  image: File | null,
  showImage: boolean,
) {
  const titleIndex = text!.search("</h1>");
  const title = text!.substring(4, titleIndex);
  const content = text!.substring(titleIndex + 5);

  if (!title || title.length === 0) throw new Error("A title is required.");

  if (!content || content.length === 0)
    throw new Error("Post content is required.");

  if (!image && !showImage) throw new Error("An image is required.");
}
