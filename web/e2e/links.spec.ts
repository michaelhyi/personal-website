import { expect, test } from "@playwright/test";
import { contact } from "../src/data/contact";
import { experience } from "../src/data/experience";
import { projects } from "../src/data/projects";

test("Links", async ({ page }) => {
   const cases: string[] = [
      "http://localhost:3000/",
      "http://localhost:3000/projects",
      "http://localhost:3000/lauren",
      "http://localhost:3000/1",
      "http://localhost:3000/projects/1",
      "http://localhost:3000/lauren/1",
   ];

   let links: string[] = [];

   contact.forEach((v) => {
      links.push(v.href);
   });

   projects.forEach((v) => {
      links.push(v.href);
   });

   experience.forEach((v) => {
      links.push(v.href);
   });

   links.push("https://michael-yi.com/Resume.pdf");

   let assert = true;

   for (let i = 0; i < cases.length; i++) {
      const v = cases[i];

      if (!assert) return;

      await page.goto(v);

      const hrefs = await page.locator("a:visible");
      const len = await hrefs.count();

      for (let i = 0; i < len; i++) {
         assert = links.includes(
            (await hrefs.nth(i).getAttribute("href")) as string
         );
      }
   }

   await expect(assert).toBe(true);
});
