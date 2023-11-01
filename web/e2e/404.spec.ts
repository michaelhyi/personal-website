import { expect, test } from "@playwright/test";

test("Routes", async ({ page }) => {
   await page.goto("http://localhost:3000/1");
   await expect(page).toHaveURL("http://localhost:3000/1");
   await expect(page.getByText("404, not found.")).toBeVisible();

   await page.goto("http://localhost:3000/lauren/1");
   await expect(page).toHaveURL("http://localhost:3000/lauren/1");
   await expect(page.getByText("404, not found.")).toBeVisible();

   await page.goto("http://localhost:3000/projects/1");
   await expect(page).toHaveURL("http://localhost:3000/projects/1");
   await expect(page.getByText("404, not found.")).toBeVisible();
});
