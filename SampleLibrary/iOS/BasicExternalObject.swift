
import Foundation
import GXCoreBL
import GXCoreUI
import GXDataLayer

@objc(BasicExternalObject)
public class BasicExternalObject: GXActionExternalObjectHandler {

	public static let classIdentifier = "BasicExternalObject"

	override public class func handleActionExecutionUsingMethodHandlerSelectorNamePrefix() -> Bool {
		return true
	}

	//MARK: - External object methods: Message and Hello

	@objc public func gxActionExObjMethodHandler_Message() {
		guard let message = self.readParameter() else {
			let error = NSError.wrongNumberOfParametersDeveloperError(forMethod: self.actionExObjDesc.actionExternalObjectMethod)
			self.onFinishedExecutingWithError(error)
			return
		}
		self.showToast(message: message)
		self.onFinishedExecutingWithSuccess()
    }

	@objc public func gxActionExObjMethodHandler_Hello() {
		let hello = "Hello World!"
		self.showToast(message: hello)
		self.onFinishedExecutingWithSuccess()
	}

	//MARK: - Private
	
	private func readParameter() -> String? {
		guard let actionParameterArray = self.actionExObjDesc.actionParametersDescriptor??.actionParametersDescriptors,
			actionParameterArray.count == 1 else {
			return nil
		}
		let paramValue = self.readStringParameter(actionParameterArray[0], from: self.contextEntityData())
		return GXUtilities.nonEmptyString(from: paramValue)
	}
	
	//MARK: - Internal implementation

	private func showToast(message : String) {
		// Ideas from https://stackoverflow.com/questions/31540375/how-to-toast-message-in-swift
		// First get the controller and it's view
		if let rootController = GXExecutionEnvironmentHelper.keyWindow?.rootViewController {
			// Build the Toast sample
			let size = rootController.view.frame.size
			let toastLabel = UILabel(frame: CGRect(x: (size.width)/2 - 75, y: (size.height) - 100, width: 150, height: 35))
			toastLabel.backgroundColor = UIColor.black.withAlphaComponent(0.6)
			toastLabel.textColor = UIColor.white
			toastLabel.textAlignment = .center;
			toastLabel.font = UIFont(name: "Montserrat-Light", size: 12.0)
			toastLabel.text = message
			toastLabel.alpha = 1.0
			toastLabel.layer.cornerRadius = 10;
			toastLabel.clipsToBounds  =  true
			
			rootController.view.addSubview(toastLabel)
			
			UIView.animate(withDuration: 4.0, delay: 0.1, options: .curveEaseOut, animations: {
				toastLabel.alpha = 0.0
			}, completion: {(isCompleted) in
				toastLabel.removeFromSuperview()
			})
		}
	}
}
