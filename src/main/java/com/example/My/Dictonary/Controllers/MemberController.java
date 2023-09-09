package com.example.My.Dictonary.Controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.My.Dictonary.Entities.Member;
import com.example.My.Dictonary.Services.DictionaryService;
import com.example.My.Dictonary.Services.LevelService;
import com.example.My.Dictonary.Services.MemberDictionaryService;
import com.example.My.Dictonary.Services.MemberService;
import com.example.My.Dictonary.Services.PaymentService;
import com.example.My.Dictonary.Services.WordService;

@Controller
public class MemberController {

	@Autowired
	MemberService memberService;

	@Autowired
	MemberDictionaryService memberDictionaryService;

	@Autowired
	DictionaryService dictionaryService;

	@Autowired
	WordService wordService;

	@Autowired
	ModelMapper modelmapper;

	@Autowired
	LevelService levelService;

	@Autowired
	PaymentService paymentService;

	@GetMapping(value = "/memberModification")
	public String memberModification(HttpSession session, Model model) {
		Long memberIdAuto = (Long) session.getAttribute("member_idAuto");
		Member member = memberService.findByIdAuto(memberIdAuto).get();

		String nickName = member.getNickname();
		boolean memberNicknameIsPublic = member.isNicknameIsPublic();
		boolean memberEmailIsPublic = member.isEmailIsPublic();
		boolean memberExpIsPublic = member.isExpIsPublic();
		boolean memberPostIsPublic = member.isPostIsPublic();
		boolean memberCommentIsPublic = member.isCommentIsPublic();

		model.addAttribute("nickName", nickName);
		
		List<Long> levelAndExp = levelService.getLevel(session);
		model.addAttribute("level", levelAndExp.get(0));
		model.addAttribute("exp", levelAndExp.get(1));
		model.addAttribute("exp_ratio", levelAndExp.get(2));

		return "/MemberInfo/MemberModification";
	}

	@PostMapping(value = "/memberModification/upload_photo")
	public String saveProfile(MultipartFile file, HttpSession session) throws IOException {

		if (!file.getContentType().startsWith("image")) {
			System.out.println("This is not an image file.");
			return "redirect:/memberModification";
		}
		String originalName = file.getOriginalFilename();
		String fileName = originalName.substring(originalName.lastIndexOf("//") + 1);

		String folderPath = makeFolder();
		String[] folderPathForDB_1 = folderPath.split("\\\\");
		String folderPathForDB_2 = folderPathForDB_1[folderPathForDB_1.length - 1];
		String uuid = UUID.randomUUID().toString();
		String pathToSave = folderPath + "\\" + uuid + "_" + fileName;
		String pathForDB = "/photo/" + uuid + "_" + fileName;

		Path savedPath = Paths.get(pathToSave);

		try {
			Long memberIdAuto = (Long) session.getAttribute("member_idAuto");
			Member member = memberService.findByIdAuto(memberIdAuto).get();
			String folderPathFromDB = member.getPhotoFolder();

			String photoPath_1 = member.getPhoto();
			if (photoPath_1 != null) {
				String[] photoPath_2 = photoPath_1.split("/");
				String photoPath_3 = photoPath_2[photoPath_2.length - 1];
				String fullPath = "C:\\upload\\" + folderPathFromDB + "\\" + photoPath_3;

				// deleting an existing photo
				File fileToDelete = new File(fullPath);

				if (fileToDelete.exists()) {

					fileToDelete.delete();
					System.out.println("deleted successfully.");

				} else {
					System.out.println("The file doesn't exist.");
				}
			} else {
				System.out.println("The photoPath_1 is null.");
			}

			// updating as a new photo
			file.transferTo(savedPath);

			member.setPhoto(pathForDB);
			member.setPhotoFolder(folderPathForDB_2);
			memberService.saveOne(member);
			session.setAttribute("member_photo", member.getPhoto());

		} catch (IOException e) {

			e.printStackTrace();

		}

		return "redirect:/memberModification";
	}

	private String makeFolder() {

		String now = LocalDate.now().toString();

		String path = "C:\\upload\\" + now;

		File folder = new File(path);

		if (!folder.exists()) {
			folder.mkdir();
		}

		return path;
	}

	@GetMapping(value = "/memberModification/delete_photo")
	public String deleteProfile(HttpSession session) {

		Long memberIdAuto = (Long) session.getAttribute("member_idAuto");
		Member member = memberService.findByIdAuto(memberIdAuto).get();
		String folderPath = member.getPhotoFolder();

		String photoPath_1 = member.getPhoto();
		if (photoPath_1 != null) {
			String[] photoPath_2 = photoPath_1.split("/");
			String photoPath_3 = photoPath_2[photoPath_2.length - 1];
			String fullPath = "C:\\upload\\" + folderPath + "\\" + photoPath_3;

			// deleting an existing photo
			File file = new File(fullPath);

			if (file.exists()) {

				file.delete();
				System.out.println("deleted successfully.");

			} else {
				System.out.println("The file doesn't exist.");
			}
		} else {
			System.out.println("The photoPath_1 is null.");
		}

		// updating as a default photo
		member.setPhoto(null);
		memberService.saveOne(member);

		session.setAttribute("member_photo", member.getPhoto());

		return "redirect:/memberModification";
	}

	// introducing premium service
	@GetMapping(value = "/comparison")
	public String comparisonGet() {
		return "/MemberInfo/Comparison";
	}

	@PostMapping(value = "/comparison")
	public String comparisonPost() throws IOException {

		String token = paymentService.getToken();
		System.out.println("토큰 : " + token);

		return "/MemberInfo/Comparison";
	}

	// sign out
	@GetMapping(value = "/deleteSession")
	public String deleteSession(HttpServletRequest request) {

		HttpSession session = request.getSession();
		session.invalidate();

		return "redirect:/";

	}

}
